package org.test.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EnumFactory {
  private static final byte BYTE_VALUE = 0;
  private static final short SHORT_VALUE = 0;
  private static final int INT_VALUE = 0;
  private static final long LONG_VALUE = 0;
  private static final float FLOAT_VALUE = 0;
  private static final double DOUBLE_VALUE = 0;
  private static final boolean BOOLEAN_VALUE = false;
  private static final char CHAR_VALUE = '0';

  static Object newInstance(Class<?> enumClass) {
    if (!enumClass.isEnum()) {
      throw new IllegalArgumentException("Class " + enumClass + " is not enum");
    }

    Constructor<?> constructor = enumClass.getDeclaredConstructors()[0];
    constructor.setAccessible(true);

    try {
      Object constructorAccessor = getConstructorAccessor(constructor);
      Object[] parameterValues = getParameterValues(constructor);
      Object instance = invokeNewInstanceMethod(constructorAccessor, parameterValues);
      return enumClass.cast(instance);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
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

  private static Object[] getParameterValues(Constructor<?> constructor) {
    Class<?>[] parameterTypes = constructor.getParameterTypes();
    Object[] parameterValues = new Object[parameterTypes.length];

    parameterValues[0] = "DUMMY"; // enum name
    parameterValues[1] = 0; // enum ordinal

    // custom parameters, if present
    for (int i = 2; i < parameterTypes.length; i++) {
      parameterValues[i] = getValue(parameterTypes[i]);
    }

    return parameterValues;
  }

  private static Object getValue(Class<?> type) {
    return type.isPrimitive() ? getPrimitiveValue(type) : null;
  }

  private static Object getPrimitiveValue(Class<?> type) {
    switch (type.getName()) {
      case "byte": return BYTE_VALUE;
      case "short": return SHORT_VALUE;
      case "int": return INT_VALUE;
      case "long": return LONG_VALUE;
      case "float": return FLOAT_VALUE;
      case "double": return DOUBLE_VALUE;
      case "boolean": return BOOLEAN_VALUE;
      case "char": return CHAR_VALUE;
      default: return null;
    }
  }

  private static Object invokeNewInstanceMethod(Object constructorAccessor, Object[] parameters) throws ReflectiveOperationException {
    Method method = constructorAccessor.getClass().getMethod("newInstance", Object[].class);
    method.setAccessible(true);
    return method.invoke(constructorAccessor, new Object[] {parameters});
  }
}
