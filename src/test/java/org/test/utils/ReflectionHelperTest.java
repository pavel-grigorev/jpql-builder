package org.test.utils;

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
