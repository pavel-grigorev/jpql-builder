package org.thepavel.jpqlbuilder.factory;

public interface InstanceFactory {
  <T> T newInstance(Class<T> type) throws ReflectiveOperationException;
}
