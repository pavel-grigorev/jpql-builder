package org.test.factory;

import org.junit.Before;
import org.junit.Test;
import org.test.model.Status;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertNotSame;

public class DefaultInstanceFactoryTest {
  @Test
  public void newEnum() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Status.class), factory.newInstance(Status.class));
  }

  @Test
  public void newByte() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Byte.class), factory.newInstance(Byte.class));
  }

  @Test
  public void newShort() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Short.class), factory.newInstance(Short.class));
  }

  @Test
  public void newInteger() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Integer.class), factory.newInstance(Integer.class));
  }

  @Test
  public void newLong() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Long.class), factory.newInstance(Long.class));
  }

  @Test
  public void newFloat() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Float.class), factory.newInstance(Float.class));
  }

  @Test
  public void newDouble() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Double.class), factory.newInstance(Double.class));
  }

  @Test
  public void newBoolean() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Boolean.class), factory.newInstance(Boolean.class));
  }

  @Test
  public void newCharacter() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Character.class), factory.newInstance(Character.class));
  }

  @Test
  public void newBigDecimal() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(BigDecimal.class), factory.newInstance(BigDecimal.class));
  }

  @Test
  public void newUuid() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(UUID.class), factory.newInstance(UUID.class));
  }

  @Test
  public void newString() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(String.class), factory.newInstance(String.class));
  }

  @Test(expected = InstantiationException.class)
  public void noDefaultConstructor() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(TestClass.class), factory.newInstance(TestClass.class));
  }

  private InstanceFactory factory;

  @Before
  public void setup() {
    factory = new DefaultInstanceFactory();
  }

  private static class TestClass {
    private TestClass(@SuppressWarnings("unused") String s) {
    }
  }
}
