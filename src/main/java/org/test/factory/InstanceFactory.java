package org.test.factory;

public interface InstanceFactory {
  <T> T newInstance(Class<T> type) throws ReflectiveOperationException;
}
