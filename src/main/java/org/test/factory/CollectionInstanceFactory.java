package org.test.factory;

import java.util.Collection;

public interface CollectionInstanceFactory {
  <T extends Collection<E>, E> T newInstance(Class<?> type) throws ReflectiveOperationException;
}
