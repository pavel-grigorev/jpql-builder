package org.test.factory;

import java.lang.reflect.Constructor;

public interface ConstructorParametersFactory {
  Object[] getParameters(Constructor<?> constructor);
}
