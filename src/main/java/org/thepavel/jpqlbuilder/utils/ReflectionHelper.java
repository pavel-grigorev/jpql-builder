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
