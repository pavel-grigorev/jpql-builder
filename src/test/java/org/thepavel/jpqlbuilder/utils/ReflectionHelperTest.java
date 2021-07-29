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

package org.thepavel.jpqlbuilder.utils;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ReflectionHelperTest {
  @Test
  public void newInstance() {
    Object map = ReflectionHelper.newInstance(HashMap.class);
    assertTrue(map instanceof HashMap);
    assertNotSame(map, ReflectionHelper.newInstance(HashMap.class));
  }

  @Test
  public void genericReturnTypes() throws NoSuchMethodException {
    Method method = TestClass.class.getMethod("getMap");
    ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
    Class<?>[] types = ReflectionHelper.getGenericReturnTypes(type);

    assertEquals(2, types.length);
    assertSame(Long.class, types[0]);
    assertSame(String.class, types[1]);
  }

  @Test
  public void genericReturnType() throws NoSuchMethodException {
    Method method = TestClass.class.getMethod("getStringList");
    ParameterizedType type = (ParameterizedType) method.getGenericReturnType();

    assertSame(String.class, ReflectionHelper.getGenericReturnType(type));
  }

  @Test
  public void defaultConstructor() {
    Constructor<?> constructor = ReflectionHelper.getDefaultConstructor(TestClass.class);

    assertNotNull(constructor);
    assertEquals(0, constructor.getParameterCount());
  }

  @Test(expected = IllegalArgumentException.class)
  public void noDefaultConstructor() {
    ReflectionHelper.getDefaultConstructor(NoDefaultConstructor.class);
  }

  @Test
  public void getterMethods() {
    Set<String> methodNames = ReflectionHelper
        .getGetterMethods(TestClass.class)
        .stream()
        .map(Method::getName)
        .collect(Collectors.toSet());

    assertEquals(new HashSet<>(asList("getMap", "getStringList", "isX", "isY")), methodNames);
  }

  @Test
  public void propertyName() throws NoSuchMethodException {
    assertEquals("map", ReflectionHelper.getPropertyName(TestClass.class.getMethod("getMap")));
    assertEquals("x", ReflectionHelper.getPropertyName(TestClass.class.getMethod("isX")));
  }

  private static class TestClass {
    public Map<Long, String> getMap() {
      return null;
    }

    public List<String> getStringList() {
      return null;
    }

    // Not a getter - returns void
    public void getA() {
    }

    // Not a getter - has parameter
    public String getX(String s) {
      return null;
    }

    // Not a getter - does not start with "get" or "is"
    public String setX() {
      return null;
    }

    public boolean isX() {
      return false;
    }

    public Boolean isY() {
      return null;
    }

    // Not a getter - starts with "is" and does not return boolean/Boolean
    public String isZ() {
      return null;
    }
  }

  private static class NoDefaultConstructor {
    public NoDefaultConstructor(String s) {
    }
  }
}
