package org.test.utils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public class ReflectionHelper {
  public static Class<?> getGenericReturnType(Method method) {
    ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
    return  (Class<?>) genericType.getActualTypeArguments()[0];
  }
}
