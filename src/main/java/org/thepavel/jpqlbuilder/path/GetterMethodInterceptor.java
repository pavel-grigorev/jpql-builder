/*
 * Copyright (c) 2020 Pavel Grigorev.
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

package org.thepavel.jpqlbuilder.path;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.thepavel.jpqlbuilder.utils.ReflectionHelper;
import org.thepavel.jpqlbuilder.utils.EntityHelper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GetterMethodInterceptor implements MethodInterceptor {
  private static final List<String> PREFIXES = Arrays.asList("get", "is");

  private final PathResolver<?> pathResolver;

  GetterMethodInterceptor(PathResolver<?> pathResolver) {
    this.pathResolver = pathResolver;
  }

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    Method method = invocation.getMethod();

    if (!isGetter(method)) {
      return invocation.proceed();
    }

    String propertyName = getPropertyName(method);
    Object value = pathResolver.getValue(propertyName);
    if (value != null) {
      return value;
    }

    value = getReturnValue(method, propertyName);
    pathResolver.put(propertyName, value);
    return value;
  }

  private static boolean isGetter(Method method) {
    return getPrefix(method).isPresent() &&
        hasNoParameters(method) &&
        isNotStatic(method);
  }

  private static Optional<String> getPrefix(Method method) {
    String name = method.getName();
    return PREFIXES
        .stream()
        .filter(name::startsWith)
        .findFirst();
  }

  private static boolean hasNoParameters(Method method) {
    return method.getParameterTypes().length == 0;
  }

  private static boolean isNotStatic(Method method) {
    return !Modifier.isStatic(method.getModifiers());
  }

  private static String getPropertyName(Method method) {
    int offset = getPrefix(method)
        .map(String::length)
        .orElse(0);

    String propertyName = method.getName().substring(offset);
    return StringUtils.uncapitalize(propertyName);
  }

  private Object getReturnValue(Method method, String propertyName) throws ReflectiveOperationException {
    Class<?> returnType = method.getReturnType();

    if (EntityHelper.isEntity(returnType)) {
      return pathResolver
          .createChildResolver(returnType, propertyName)
          .getPathSpecifier();
    }

    if (Collection.class.isAssignableFrom(returnType)) {
      Class<?> genericClass = ReflectionHelper.getGenericReturnType(method);
      Object instance = newInstance(genericClass);

      Collection<Object> collection = newCollectionInstance(returnType);
      collection.add(instance);
      return collection;
    }

    if (Map.class.isAssignableFrom(returnType)) {
      Class<?>[] genericClasses = ReflectionHelper.getGenericReturnTypes(method);
      Object key = newInstance(genericClasses[0]);
      Object value = newInstance(genericClasses[1]);

      Map<Object, Object> map = newMapInstance(returnType);
      map.put(key, value);
      return map;
    }

    return newInstance(returnType);
  }

  private Object newInstance(Class<?> type) throws ReflectiveOperationException {
    return pathResolver
        .getContext()
        .newInstance(type);
  }

  private Collection<Object> newCollectionInstance(Class<?> type) throws ReflectiveOperationException {
    return pathResolver
        .getContext()
        .newCollectionInstance(type);
  }

  private Map<Object, Object> newMapInstance(Class<?> type) throws ReflectiveOperationException {
    return pathResolver
        .getContext()
        .newMapInstance(type);
  }
}
