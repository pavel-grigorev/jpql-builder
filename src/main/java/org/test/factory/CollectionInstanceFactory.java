package org.test.factory;

import java.util.Collection;

public interface CollectionInstanceFactory {
  Collection<Object> newInstance(Class<?> type) throws ReflectiveOperationException;
}
