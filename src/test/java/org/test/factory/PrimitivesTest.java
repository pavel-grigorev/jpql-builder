package org.test.factory;

import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PrimitivesTest {
  @Test
  public void newByte() {
    assertNotSame(Primitives.newByte(), Primitives.newByte());
  }

  @Test
  public void newShort() {
    assertNotSame(Primitives.newShort(), Primitives.newShort());
  }

  @Test
  public void newInteger() {
    assertNotSame(Primitives.newInteger(), Primitives.newInteger());
  }

  @Test
  public void newLong() {
    assertNotSame(Primitives.newLong(), Primitives.newLong());
  }

  @Test
  public void newFloat() {
    assertNotSame(Primitives.newFloat(), Primitives.newFloat());
  }

  @Test
  public void newDouble() {
    assertNotSame(Primitives.newDouble(), Primitives.newDouble());
  }

  @Test
  public void newBoolean() {
    assertNotSame(Primitives.newBoolean(), Primitives.newBoolean());
  }

  @Test
  public void newCharacter() {
    assertNotSame(Primitives.newCharacter(), Primitives.newCharacter());
  }

  @Test
  public void getByte() {
    assertTrue(Primitives.get(byte.class) instanceof Byte);
    assertNull(Primitives.get(Byte.class));
  }

  @Test
  public void getShort() {
    assertTrue(Primitives.get(short.class) instanceof Short);
    assertNull(Primitives.get(Short.class));
  }

  @Test
  public void getInt() {
    assertTrue(Primitives.get(int.class) instanceof Integer);
    assertNull(Primitives.get(Integer.class));
  }

  @Test
  public void getLong() {
    assertTrue(Primitives.get(long.class) instanceof Long);
    assertNull(Primitives.get(Long.class));
  }

  @Test
  public void getFloat() {
    assertTrue(Primitives.get(float.class) instanceof Float);
    assertNull(Primitives.get(Float.class));
  }

  @Test
  public void getDouble() {
    assertTrue(Primitives.get(double.class) instanceof Double);
    assertNull(Primitives.get(Double.class));
  }

  @Test
  public void getBoolean() {
    assertTrue(Primitives.get(boolean.class) instanceof Boolean);
    assertNull(Primitives.get(Boolean.class));
  }

  @Test
  public void getChar() {
    assertTrue(Primitives.get(char.class) instanceof Character);
    assertNull(Primitives.get(Character.class));
  }
}
