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

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class ProxyClassCacheTest {
  @Test
  public void cachesClasses() {
    Class<?> class1 = ProxyClassCache.getProxyClassFor(ProxyClassCacheTest.class);
    Class<?> class2 = ProxyClassCache.getProxyClassFor(ProxyClassCacheTest.class);

    assertSame(class1, class2);
  }
}
