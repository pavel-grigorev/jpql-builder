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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
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
    Class<?>[] types = ReflectionHelper.getGenericReturnTypes(method);

    assertEquals(2, types.length);
    assertSame(Long.class, types[0]);
    assertSame(String.class, types[1]);
  }

  @Test
  public void genericReturnType() throws NoSuchMethodException {
    Method method = TestClass.class.getMethod("getStringList");
    Class<?> type = ReflectionHelper.getGenericReturnType(method);

    assertSame(String.class, type);
  }

  @Test(expected = IllegalArgumentException.class)
  public void unspecifiedType() throws NoSuchMethodException {
    Method method = TestClass.class.getMethod("getRawList");
    ReflectionHelper.getGenericReturnType(method);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noGenerics() throws NoSuchMethodException {
    Method method = TestClass.class.getMethod("getString");
    ReflectionHelper.getGenericReturnType(method);
  }

  private static class TestClass {
    public Map<Long, String> getMap() {
      return null;
    }

    public List<String> getStringList() {
      return null;
    }

    @SuppressWarnings("rawtypes")
    public List getRawList() {
      return null;
    }

    public String getString() {
      return null;
    }
  }
}
