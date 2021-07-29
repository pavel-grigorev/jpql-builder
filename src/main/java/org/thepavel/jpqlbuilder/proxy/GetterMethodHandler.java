/*
 * Copyright (c) 2020-2021 Pavel Grigorev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thepavel.jpqlbuilder.proxy;

import org.thepavel.jpqlbuilder.JpqlBuilderContext;
import org.thepavel.jpqlbuilder.path.PathResolver;
import org.thepavel.jpqlbuilder.utils.EntityHelper;
import org.thepavel.jpqlbuilder.utils.ReflectionHelper;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

public class GetterMethodHandler {
  public static final Method HANDLER_METHOD = ReflectionHelper
      .getMethod(GetterMethodHandler.class, "invoke", String.class, Type.class, PathResolver.class);

  public static Object invoke(String propertyName, Type returnType, PathResolver<?> pathResolver) {
    Object value = pathResolver.getValue(propertyName);
    if (value != null) {
      return value;
    }

    value = getReturnValue(propertyName, returnType, pathResolver);
    pathResolver.put(propertyName, value);

    return value;
  }

  private static Object getReturnValue(String propertyName, Type returnType, PathResolver<?> pathResolver) {
    JpqlBuilderContext context = pathResolver.getContext();

    if (returnType instanceof ParameterizedType) {
      return getValueForParameterizedType((ParameterizedType) returnType, context);
    }

    Class<?> rawClass = (Class<?>) returnType;

    if (EntityHelper.isEntity(rawClass)) {
      return pathResolver
          .createChildResolver(rawClass, propertyName)
          .getPathSpecifier();
    }

    return context.newInstance(rawClass);
  }

  private static Object getValueForParameterizedType(ParameterizedType type, JpqlBuilderContext context) {
    Class<?> rawClass = (Class<?>) type.getRawType();

    if (Collection.class.isAssignableFrom(rawClass)) {
      return newCollection(type, rawClass, context);
    }

    if (Map.class.isAssignableFrom(rawClass)) {
      return newMap(type, rawClass, context);
    }

    return context.newInstance(rawClass);
  }

  private static Collection<Object> newCollection(ParameterizedType type, Class<?> rawClass, JpqlBuilderContext context) {
    Class<?> genericClass = ReflectionHelper.getGenericReturnType(type);
    Object instance = context.newInstance(genericClass);

    Collection<Object> collection = context.newCollectionInstance(rawClass);
    collection.add(instance);
    return collection;
  }

  private static Map<Object, Object> newMap(ParameterizedType type, Class<?> rawClass, JpqlBuilderContext context) {
    Class<?>[] genericClasses = ReflectionHelper.getGenericReturnTypes(type);
    Object key = context.newInstance(genericClasses[0]);
    Object value = context.newInstance(genericClasses[1]);

    Map<Object, Object> map = context.newMapInstance(rawClass);
    map.put(key, value);
    return map;
  }
}
