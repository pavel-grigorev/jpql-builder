package org.test;

import org.aopalliance.aop.Advice;
import org.test.factory.CollectionInstanceFactory;
import org.test.factory.InstanceFactory;
import org.test.factory.ProxyFactory;

import java.util.Collection;

public class JpqlBuilderContext {
  private final InstanceFactory instanceFactory;
  private final CollectionInstanceFactory collectionInstanceFactory;
  private final ProxyFactory proxyFactory;

  public JpqlBuilderContext(
      InstanceFactory instanceFactory,
      CollectionInstanceFactory collectionInstanceFactory,
      ProxyFactory proxyFactory
  ) {
    this.instanceFactory = instanceFactory;
    this.collectionInstanceFactory = collectionInstanceFactory;
    this.proxyFactory = proxyFactory;
  }

  public <T> T newInstance(Class<T> type) throws ReflectiveOperationException {
    return instanceFactory.newInstance(type);
  }

  public Collection<Object> newCollectionInstance(Class<?> type) throws ReflectiveOperationException {
    return collectionInstanceFactory.newInstance(type);
  }

  public <T> T createProxy(Class<T> type, Advice advice) {
    return proxyFactory.createProxy(type, advice);
  }

  public <T> T createProxy(T target, Advice advice) {
    return proxyFactory.createProxy(target, advice);
  }
}
