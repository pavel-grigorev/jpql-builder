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

package org.thepavel.jpqlbuilder.utils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionHelper {
  @SuppressWarnings("unchecked")
  public static <T> T newInstance(Class<?> type) {
    try {
      return (T) type.newInstance();
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  public static Class<?> getGenericReturnType(Method method) {
    return getGenericReturnTypes(method)[0];
  }

  public static Class<?>[] getGenericReturnTypes(Method method) {
    Type returnType = method.getGenericReturnType();

    if (!(returnType instanceof ParameterizedType)) {
      throw new IllegalArgumentException("No generics in return type of method " + method.getName());
    }

    ParameterizedType parameterizedType = (ParameterizedType) returnType;
    return toClass(parameterizedType.getActualTypeArguments());
  }

  private static Class<?>[] toClass(Type[] types) {
    Class<?>[] classes = new Class[types.length];
    for (int i = 0; i < types.length; i++) {
      classes[i] = (Class<?>) types[i];
    }
    return classes;
  }
}
