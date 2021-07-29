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

package org.thepavel.jpqlbuilder.proxy;

import org.thepavel.jpqlbuilder.path.PathResolver;

public class EntityProxyFactory {
  private EntityProxyFactory() {
  }

  @SuppressWarnings("unchecked")
  public static <T> T createProxy(Class<T> type, PathResolver<T> pathResolver) {
    return (T) newInstance(ProxyClassCache.getProxyClassFor(type), pathResolver);
  }

  private static Object newInstance(Class<?> proxyClass, PathResolver<?> pathResolver) {
    try {
      return proxyClass.getConstructor(PathResolver.class).newInstance(pathResolver);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException("Can not create instance of class " + proxyClass.getName(), e);
    }
  }
}
