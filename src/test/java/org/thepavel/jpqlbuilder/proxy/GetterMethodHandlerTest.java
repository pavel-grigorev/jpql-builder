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
import org.thepavel.jpqlbuilder.JpqlBuilderContext;
import org.thepavel.jpqlbuilder.path.PathResolver;

import javax.persistence.Entity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class GetterMethodHandlerTest {
  private static JpqlBuilderContext context;

  @SuppressWarnings("unused")
  private List<String> listField;

  @SuppressWarnings("unused")
  private Set<String> setField;

  @SuppressWarnings("unused")
  private Map<String, String> mapField;

  @BeforeClass
  public static void setup() {
    context = JpqlBuilderContext.defaultContext();
  }

  @AfterClass
  public static void tearDown() {
    context = null;
  }

  @Test
  public void returnsValueFromPathResolver() {
    PathResolver<?> pathResolver = new PathResolver<>(GetterMethodHandlerTest.class, "a", context);
    pathResolver.put("name", "Peter");

    Object value = GetterMethodHandler.invoke("name", String.class, pathResolver);
    assertEquals("Peter", value);
  }

  @Test
  public void returnsNewInstanceIfNotInPathResolverAlready() {
    PathResolver<?> pathResolver = new PathResolver<>(GetterMethodHandlerTest.class, "a", context);

    Object value = GetterMethodHandler.invoke("name", String.class, pathResolver);
    assertNotNull(value);
    assertSame(String.class, value.getClass());

    Object value1 = GetterMethodHandler.invoke("name", String.class, pathResolver);
    assertSame(value, value1);
  }

  @Test
  public void returnsProxyEntityInstance() {
    PathResolver<?> pathResolver = new PathResolver<>(GetterMethodHandlerTest.class, "a", context);

    Object value = GetterMethodHandler.invoke("ref", TestEntity.class, pathResolver);
    assertNotNull(value);
    assertTrue(value instanceof TestEntity);
    assertNotSame(TestEntity.class, value.getClass());

    Object value1 = GetterMethodHandler.invoke("ref", TestEntity.class, pathResolver);
    assertSame(value, value1);
  }

  @Test
  public void returnsNewInstanceOfList() {
    PathResolver<?> pathResolver = new PathResolver<>(GetterMethodHandlerTest.class, "a", context);

    Object value = GetterMethodHandler.invoke("ref", getType("listField"), pathResolver);
    assertNotNull(value);
    assertSame(ArrayList.class, value.getClass());

    List<?> list = (List<?>) value;
    assertEquals(1, list.size());
    assertNotNull(list.get(0));
    assertSame(String.class, list.get(0).getClass());
  }

  @Test
  public void returnsNewInstanceOfSet() {
    PathResolver<?> pathResolver = new PathResolver<>(GetterMethodHandlerTest.class, "a", context);

    Object value = GetterMethodHandler.invoke("ref", getType("setField"), pathResolver);
    assertNotNull(value);
    assertSame(HashSet.class, value.getClass());

    Set<?> set = (Set<?>) value;
    assertEquals(1, set.size());

    Object item = set.iterator().next();
    assertNotNull(item);
    assertSame(String.class, item.getClass());
  }

  @Test
  public void returnsNewInstanceOfMap() {
    PathResolver<?> pathResolver = new PathResolver<>(GetterMethodHandlerTest.class, "a", context);

    Object value = GetterMethodHandler.invoke("ref", getType("mapField"), pathResolver);
    assertNotNull(value);
    assertSame(HashMap.class, value.getClass());

    Map<?, ?> map = (Map<?, ?>) value;
    assertEquals(1, map.size());
    assertNotNull(map.get(""));
    assertSame(String.class, map.get("").getClass());
  }

  private static Type getType(String fieldName) {
    try {
      return GetterMethodHandlerTest.class.getDeclaredField(fieldName).getGenericType();
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @Entity
  public static class TestEntity {
  }
}
