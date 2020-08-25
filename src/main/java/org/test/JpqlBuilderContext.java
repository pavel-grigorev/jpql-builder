package org.test;

import org.test.factory.CollectionInstanceFactory;
import org.test.factory.InstanceFactory;

import java.util.Collection;

public class JpqlBuilderContext {
  private final InstanceFactory instanceFactory;
  private final CollectionInstanceFactory collectionInstanceFactory;

  public JpqlBuilderContext(InstanceFactory instanceFactory, CollectionInstanceFactory collectionInstanceFactory) {
    this.instanceFactory = instanceFactory;
    this.collectionInstanceFactory = collectionInstanceFactory;
  }

  public <T> T newInstance(Class<T> type) throws ReflectiveOperationException {
    return instanceFactory.newInstance(type);
  }

  public Collection<Object> newCollectionInstance(Class<?> type) throws ReflectiveOperationException {
    return collectionInstanceFactory.newInstance(type);
  }
}
