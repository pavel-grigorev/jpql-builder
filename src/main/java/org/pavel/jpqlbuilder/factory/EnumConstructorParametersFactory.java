package org.pavel.jpqlbuilder.factory;

import java.lang.reflect.Constructor;

public class EnumConstructorParametersFactory implements ConstructorParametersFactory {
  @Override
  public Object[] getParameters(Constructor<?> constructor) {
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
    return type.isPrimitive() ? Primitives.get(type) : null;
  }
}
