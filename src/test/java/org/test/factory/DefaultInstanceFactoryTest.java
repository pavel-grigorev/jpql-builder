package org.test.factory;

import org.junit.Before;
import org.junit.Test;
import org.test.model.Status;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;
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
  public void newBigInteger() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(BigInteger.class), factory.newInstance(BigInteger.class));
  }

  @Test
  public void newUuid() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(UUID.class), factory.newInstance(UUID.class));
  }

  @Test
  public void newJavaSqlDate() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(java.sql.Date.class), factory.newInstance(java.sql.Date.class));
  }

  @Test
  public void newJavaUtilDate() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(java.util.Date.class), factory.newInstance(java.util.Date.class));
  }

  @Test
  public void newCalendar() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Calendar.class), factory.newInstance(Calendar.class));
  }

  @Test
  public void newLocale() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Locale.class), factory.newInstance(Locale.class));
  }

  @Test
  public void newTimeZone() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(TimeZone.class), factory.newInstance(TimeZone.class));
  }

  @Test
  public void newCurrency() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Currency.class), factory.newInstance(Currency.class));
  }

  @Test
  public void newByteArray() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(byte[].class), factory.newInstance(byte[].class));
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
