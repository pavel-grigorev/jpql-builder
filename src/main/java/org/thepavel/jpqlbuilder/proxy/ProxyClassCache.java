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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ProxyClassCache {
  private static final ConcurrentMap<String, Class<?>> CACHE = new ConcurrentHashMap<>();

  private ProxyClassCache() {
  }

  public static Class<?> getProxyClassFor(Class<?> type) {
    Class<?> proxyClass = getFromCache(type);

    if (proxyClass == null) {
      synchronized (type) {
        if ((proxyClass = getFromCache(type)) == null) {
          proxyClass = ProxyClassBuilder.buildProxyClass(type);
          putToCache(type, proxyClass);
        }
      }
    }

    return proxyClass;
  }

  private static Class<?> getFromCache(Class<?> targetClass) {
    return CACHE.get(targetClass.getName());
  }

  private static void putToCache(Class<?> targetClass, Class<?> proxyClass) {
    CACHE.put(targetClass.getName(), proxyClass);
  }
}
