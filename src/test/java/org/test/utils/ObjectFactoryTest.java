package org.test.utils;

import org.junit.Test;
import org.test.model.Status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class ObjectFactoryTest {
  @Test
  public void newEnum() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(Status.class) instanceof Status);
  }

  @Test
  public void newByte() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(Byte.class) instanceof Byte);
  }

  @Test
  public void newShort() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(Short.class) instanceof Short);
  }

  @Test
  public void newInteger() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(Integer.class) instanceof Integer);
  }

  @Test
  public void newLong() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(Long.class) instanceof Long);
  }

  @Test
  public void newFloat() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(Float.class) instanceof Float);
  }

  @Test
  public void newDouble() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(Double.class) instanceof Double);
  }

  @Test
  public void newBoolean() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(Boolean.class) instanceof Boolean);
  }

  @Test
  public void newCharacter() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(Character.class) instanceof Character);
  }

  @Test
  public void newBigDecimal() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(BigDecimal.class) instanceof BigDecimal);
  }

  @Test
  public void newUuid() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(UUID.class) instanceof UUID);
  }

  @Test
  public void newString() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(String.class) instanceof String);
  }

  @Test(expected = InstantiationException.class)
  public void noDefaultConstructor() throws ReflectiveOperationException {
    assertTrue(ObjectFactory.newInstance(TestClass.class) instanceof TestClass);
  }

  @Test
  public void newList() {
    assertTrue(ObjectFactory.newCollectionInstance(List.class) instanceof List);
  }

  @Test
  public void newSet() {
    assertTrue(ObjectFactory.newCollectionInstance(Set.class) instanceof Set);
  }

  @Test(expected = IllegalArgumentException.class)
  public void unsupportedCollection() {
    assertTrue(ObjectFactory.newCollectionInstance(Queue.class) instanceof Queue);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nonCollection() {
    assertTrue(ObjectFactory.newCollectionInstance(Map.class) instanceof Map);
  }

  private static class TestClass {
    private TestClass(@SuppressWarnings("unused") String s) {
    }
  }
}
