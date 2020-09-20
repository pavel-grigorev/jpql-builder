package org.pavel.jpqlbuilder.factory;

import org.junit.Test;
import org.pavel.jpqlbuilder.model.Status;

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
    factoryCreatesUniqueInstance(Status.class);
  }

  @Test
  public void newByte() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Byte.class);
  }

  @Test
  public void newShort() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Short.class);
  }

  @Test
  public void newInteger() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Integer.class);
  }

  @Test
  public void newLong() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Long.class);
  }

  @Test
  public void newFloat() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Float.class);
  }

  @Test
  public void newDouble() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Double.class);
  }

  @Test
  public void newBoolean() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Boolean.class);
  }

  @Test
  public void newCharacter() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Character.class);
  }

  @Test
  public void newBigDecimal() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(BigDecimal.class);
  }

  @Test
  public void newBigInteger() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(BigInteger.class);
  }

  @Test
  public void newUuid() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(UUID.class);
  }

  @Test
  public void newJavaSqlDate() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(java.sql.Date.class);
  }

  @Test
  public void newJavaUtilDate() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(java.util.Date.class);
  }

  @Test
  public void newCalendar() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Calendar.class);
  }

  @Test
  public void newLocale() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Locale.class);
  }

  @Test
  public void newTimeZone() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(TimeZone.class);
  }

  @Test
  public void newCurrency() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Currency.class);
  }

  @Test
  public void newByteArray() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(byte[].class);
  }

  @Test
  public void newString() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(String.class);
  }

  @Test(expected = InstantiationException.class)
  public void noDefaultConstructor() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(TestClass.class);
  }

  private void factoryCreatesUniqueInstance(Class<?> type) throws ReflectiveOperationException {
    InstanceFactory factory = new DefaultInstanceFactory();
    assertNotSame(factory.newInstance(type), factory.newInstance(type));
  }

  public static class TestClass {
    public TestClass(@SuppressWarnings("unused") String s) {
    }
  }
}
