package org.pavel.jpqlbuilder.factory;

public interface InstanceCreator<T> {
  T newInstance() throws ReflectiveOperationException;
}
