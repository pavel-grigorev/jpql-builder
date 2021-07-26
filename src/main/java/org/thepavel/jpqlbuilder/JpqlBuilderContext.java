/*
 * Copyright (c) 2020-2021 Pavel Grigorev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thepavel.jpqlbuilder;

import org.aopalliance.aop.Advice;
import org.thepavel.jpqlbuilder.factory.InstanceFactory;
import org.thepavel.jpqlbuilder.factory.CollectionInstanceFactory;
import org.thepavel.jpqlbuilder.factory.DefaultCollectionInstanceFactory;
import org.thepavel.jpqlbuilder.factory.DefaultInstanceFactory;
import org.thepavel.jpqlbuilder.factory.DefaultMapInstanceFactory;
import org.thepavel.jpqlbuilder.factory.DefaultProxyFactory;
import org.thepavel.jpqlbuilder.factory.MapInstanceFactory;
import org.thepavel.jpqlbuilder.factory.ProxyFactory;

import java.util.Collection;
import java.util.Map;

public class JpqlBuilderContext {
  private final InstanceFactory instanceFactory;
  private final CollectionInstanceFactory collectionInstanceFactory;
  private final MapInstanceFactory mapInstanceFactory;
  private final ProxyFactory proxyFactory;

  public JpqlBuilderContext(
      InstanceFactory instanceFactory,
      CollectionInstanceFactory collectionInstanceFactory,
      MapInstanceFactory mapInstanceFactory,
      ProxyFactory proxyFactory
  ) {
    this.instanceFactory = instanceFactory;
    this.collectionInstanceFactory = collectionInstanceFactory;
    this.mapInstanceFactory = mapInstanceFactory;
    this.proxyFactory = proxyFactory;
  }

  public static JpqlBuilderContext defaultContext() {
    return new JpqlBuilderContext(
        new DefaultInstanceFactory(),
        new DefaultCollectionInstanceFactory(),
        new DefaultMapInstanceFactory(),
        new DefaultProxyFactory()
    );
  }

  public <T> T newInstance(Class<T> type) throws ReflectiveOperationException {
    return instanceFactory.newInstance(type);
  }

  public Collection<Object> newCollectionInstance(Class<?> type) throws ReflectiveOperationException {
    return collectionInstanceFactory.newInstance(type);
  }

  public Map<Object, Object> newMapInstance(Class<?> type) throws ReflectiveOperationException {
    return mapInstanceFactory.newInstance(type);
  }

  public <T> T createProxy(Class<T> type, Advice advice) {
    return proxyFactory.createProxy(type, advice);
  }
}
