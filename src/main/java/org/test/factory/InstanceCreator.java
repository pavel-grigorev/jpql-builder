package org.test.factory;

public interface InstanceCreator<T> {
  T newInstance() throws ReflectiveOperationException;
}
