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
import org.thepavel.jpqlbuilder.path.PathResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;

public class ProxyClassBuilderTest {
  private static Method originalMethodHandler;
  private static String propertyName;
  private static Type returnType;

  @BeforeClass
  public static void setup() {
    originalMethodHandler = ProxyClassBuilder.getterMethodHandler;
    try {
      ProxyClassBuilder.getterMethodHandler = ProxyClassBuilderTest.class
          .getMethod("dummy", String.class, Type.class, PathResolver.class);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @AfterClass
  public static void tearDown() {
    ProxyClassBuilder.getterMethodHandler = originalMethodHandler;
    originalMethodHandler = null;
    propertyName = null;
    returnType = null;
  }

  @Test
  public void getterMethodDelegation() {
    Class<?> proxyClass = ProxyClassBuilder.buildProxyClass(TestClass.class);
    PathResolver<?> pathResolver = null;

    TestClass proxy;
    try {
      proxy = (TestClass) proxyClass.getConstructor(PathResolver.class).newInstance(pathResolver);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    assertEquals("test", proxy.test());
    assertEquals("dummy", proxy.getString());
    assertEquals("string", propertyName);
    assertEquals(String.class, returnType);
  }

  public static Object dummy(String a, Type b, PathResolver<?> c) {
    propertyName = a;
    returnType = b;
    return "dummy";
  }

  public static class TestClass {
    public String getString() {
      return "string";
    }

    public String test() {
      return "test";
    }
  }
}
