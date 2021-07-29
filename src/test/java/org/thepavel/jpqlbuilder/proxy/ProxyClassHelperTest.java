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

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ProxyClassHelperTest {
  private static Class<?> proxyClass;

  @BeforeClass
  public static void setup() {
    proxyClass = ProxyClassBuilder.buildProxyClass(ProxyClassHelperTest.class);
  }

  @AfterClass
  public static void tearDown() {
    proxyClass = null;
  }

  @Test
  public void getProxyClassName() {
    String proxyClassName = ProxyClassHelper.getProxyClassName(ProxyClassHelperTest.class);

    assertEquals("org.thepavel.jpqlbuilder.proxy.ProxyClassHelperTest$JpqlBuilder", proxyClassName);
  }

  @Test
  public void isProxyClass() {
    assertTrue(ProxyClassHelper.isProxyClass(proxyClass));
    assertFalse(ProxyClassHelper.isProxyClass(ProxyClassHelperTest.class));
  }

  @Test
  public void getTargetClassName() {
    assertEquals("org.thepavel.jpqlbuilder.proxy.ProxyClassHelperTest$JpqlBuilder", proxyClass.getName());
    assertEquals("org.thepavel.jpqlbuilder.proxy.ProxyClassHelperTest", ProxyClassHelper.getTargetClassName(proxyClass));
  }

  @Test
  public void getTargetClass() {
    Class<?> targetClass = ProxyClassHelper.getTargetClass(proxyClass);

    assertSame(ProxyClassHelperTest.class, targetClass);
  }
}
