package org.test.utils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectionHelper {
  public static Class<?> getGenericReturnType(Method method) {
    Type returnType = method.getGenericReturnType();

    if (!(returnType instanceof ParameterizedType)) {
      throw new IllegalArgumentException("No generics in return type of method " + method.getName());
    }

    ParameterizedType genericType = (ParameterizedType) returnType;
    return  (Class<?>) genericType.getActualTypeArguments()[0];
  }
}
