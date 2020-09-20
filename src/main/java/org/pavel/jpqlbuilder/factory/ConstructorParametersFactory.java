package org.pavel.jpqlbuilder.factory;

import java.lang.reflect.Constructor;

public interface ConstructorParametersFactory {
  Object[] getParameters(Constructor<?> constructor);
}
