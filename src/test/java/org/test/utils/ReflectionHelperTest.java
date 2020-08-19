package org.test.utils;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertSame;

public class ReflectionHelperTest {
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
