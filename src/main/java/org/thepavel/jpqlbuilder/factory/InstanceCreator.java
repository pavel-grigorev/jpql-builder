package org.thepavel.jpqlbuilder.factory;

public interface InstanceCreator<T> {
  T newInstance() throws ReflectiveOperationException;
}
