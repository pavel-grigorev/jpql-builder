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

package org.thepavel.jpqlbuilder.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EnumFactory {
  private final ConstructorParametersFactory parametersFactory;

  EnumFactory() {
    this(new EnumConstructorParametersFactory());
  }

  EnumFactory(ConstructorParametersFactory parametersFactory) {
    this.parametersFactory = parametersFactory;
  }

  Object newInstance(Class<?> enumClass) {
    if (!enumClass.isEnum()) {
      throw new IllegalArgumentException("Class " + enumClass + " is not enum");
    }

    Constructor<?> constructor = getConstructor(enumClass);

    try {
      Object constructorAccessor = getConstructorAccessor(constructor);
      Object[] parameterValues = parametersFactory.getParameters(constructor);
      Object instance = invokeNewInstanceMethod(constructorAccessor, parameterValues);
      return enumClass.cast(instance);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  private static Constructor<?> getConstructor(Class<?> enumClass) {
    Constructor<?> constructor = enumClass.getDeclaredConstructors()[0];
    constructor.setAccessible(true);
    return constructor;
  }

  private static Object getConstructorAccessor(Constructor<?> constructor) throws ReflectiveOperationException {
    Object constructorAccessor = getConstructorAccessorThoughField(constructor);
    return constructorAccessor != null ? constructorAccessor : getConstructorAccessorThoughMethod(constructor);
  }

  private static Object getConstructorAccessorThoughField(Constructor<?> constructor) throws ReflectiveOperationException {
    Field field = Constructor.class.getDeclaredField("constructorAccessor");
    field.setAccessible(true);
    return field.get(constructor);
  }

  private static Object getConstructorAccessorThoughMethod(Constructor<?> constructor) throws ReflectiveOperationException {
    Method method = Constructor.class.getDeclaredMethod("acquireConstructorAccessor");
    method.setAccessible(true);
    return method.invoke(constructor);
  }

  private static Object invokeNewInstanceMethod(Object constructorAccessor, Object[] parameters) throws ReflectiveOperationException {
    Method method = constructorAccessor.getClass().getMethod("newInstance", Object[].class);
    method.setAccessible(true);
    return method.invoke(constructorAccessor, new Object[] {parameters});
  }
}
