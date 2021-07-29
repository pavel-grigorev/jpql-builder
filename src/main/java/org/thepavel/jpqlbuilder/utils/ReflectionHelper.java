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

package org.thepavel.jpqlbuilder.utils;

import java.beans.Introspector;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionHelper {
  @SuppressWarnings("unchecked")
  public static <T> T newInstance(Class<?> type) {
    try {
      return (T) type.newInstance();
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException("Can not create instance of class " + type.getName(), e);
    }
  }

  public static Class<?> getGenericReturnType(ParameterizedType type) {
    return getGenericReturnTypes(type)[0];
  }

  public static Class<?>[] getGenericReturnTypes(ParameterizedType type) {
    return toClass(type.getActualTypeArguments());
  }

  private static Class<?>[] toClass(Type[] types) {
    Class<?>[] classes = new Class[types.length];
    for (int i = 0; i < types.length; i++) {
      classes[i] = (Class<?>) types[i];
    }
    return classes;
  }

  public static Class<?> getClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("Class not found: " + className, e);
    }
  }

  public static Constructor<?> getDefaultConstructor(Class<?> type) {
    try {
      return type.getDeclaredConstructor();
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException("Class " + type.getName() + " does not have a default constructor", e);
    }
  }

  public static Method getMethod(Class<?> type, String name, Class<?>... parameterTypes) {
    try {
      return type.getMethod(name, parameterTypes);
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException("Class " + type.getName() + " does not have method '" + name + "' with parameters " + Arrays.asList(parameterTypes), e);
    }
  }

  public static List<Method> getGetterMethods(Class<?> type) {
    return Arrays
        .stream(type.getMethods())
        .filter(ReflectionHelper::isGetter)
        .collect(Collectors.toList());
  }

  private static boolean isGetter(Method method) {
    Class<?> returnType = method.getReturnType();
    if (isVoid(returnType) || method.getParameterCount() > 0) {
      return false;
    }

    String methodName = method.getName();
    if (methodName.equals("getClass")) {
      return false;
    }

    return methodName.startsWith("get") || methodName.startsWith("is") && isBoolean(returnType);
  }

  private static boolean isVoid(Class<?> type) {
    return type == void.class || type == Void.class;
  }

  private static boolean isBoolean(Class<?> type) {
    return type == boolean.class || type == Boolean.class;
  }

  public static String getPropertyName(Method getter) {
    String name = getter.getName();
    int offset = name.startsWith("get") ? 3 : 2;
    return Introspector.decapitalize(name.substring(offset));
  }
}
