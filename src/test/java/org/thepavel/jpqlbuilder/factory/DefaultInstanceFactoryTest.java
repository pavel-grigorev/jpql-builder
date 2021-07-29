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

package org.thepavel.jpqlbuilder.factory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.thepavel.jpqlbuilder.model.Status;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import static org.junit.Assert.assertNotSame;

public class DefaultInstanceFactoryTest {
  private static InstanceFactory factory;

  @BeforeClass
  public static void setup() {
    factory = new DefaultInstanceFactory();
  }

  @AfterClass
  public static void tearDown() {
    factory = null;
  }

  @Test
  public void newEnum() {
    factoryCreatesUniqueInstance(Status.class);
  }

  @Test
  public void newByte() {
    factoryCreatesUniqueInstance(Byte.class);
  }

  @Test
  public void newShort() {
    factoryCreatesUniqueInstance(Short.class);
  }

  @Test
  public void newInteger() {
    factoryCreatesUniqueInstance(Integer.class);
  }

  @Test
  public void newLong() {
    factoryCreatesUniqueInstance(Long.class);
  }

  @Test
  public void newFloat() {
    factoryCreatesUniqueInstance(Float.class);
  }

  @Test
  public void newDouble() {
    factoryCreatesUniqueInstance(Double.class);
  }

  @Test
  public void newBoolean() {
    factoryCreatesUniqueInstance(Boolean.class);
  }

  @Test
  public void newCharacter() {
    factoryCreatesUniqueInstance(Character.class);
  }

  @Test
  public void newString() {
    factoryCreatesUniqueInstance(String.class);
  }

  @Test
  public void newBigDecimal() {
    factoryCreatesUniqueInstance(BigDecimal.class);
  }

  @Test
  public void newBigInteger() {
    factoryCreatesUniqueInstance(BigInteger.class);
  }

  @Test
  public void newUuid() {
    factoryCreatesUniqueInstance(UUID.class);
  }

  @Test
  public void newJavaUtilDate() {
    factoryCreatesUniqueInstance(java.util.Date.class);
  }

  @Test
  public void newJavaSqlDate() {
    factoryCreatesUniqueInstance(java.sql.Date.class);
  }

  @Test
  public void newLocalDate() {
    factoryCreatesUniqueInstance(LocalDate.class);
  }

  @Test
  public void newLocalDateTime() {
    factoryCreatesUniqueInstance(LocalDateTime.class);
  }

  @Test
  public void newLocalTime() {
    factoryCreatesUniqueInstance(LocalTime.class);
  }

  @Test
  public void newInstant() {
    factoryCreatesUniqueInstance(Instant.class);
  }

  @Test
  public void newCalendar() {
    factoryCreatesUniqueInstance(Calendar.class);
  }

  @Test
  public void newLocale() {
    factoryCreatesUniqueInstance(Locale.class);
  }

  @Test
  public void newTimeZone() {
    factoryCreatesUniqueInstance(TimeZone.class);
  }

  @Test
  public void newCurrency() {
    factoryCreatesUniqueInstance(Currency.class);
  }

  @Test
  public void newByteArray() {
    factoryCreatesUniqueInstance(byte[].class);
  }

  @Test
  public void newCharArray() {
    factoryCreatesUniqueInstance(char[].class);
  }

  @Test
  public void defaultConstructor() {
    factoryCreatesUniqueInstance(DefConstructorClass.class);
  }

  @Test
  public void noDefaultConstructor() {
    factoryCreatesUniqueInstance(NoDefConstructorClass.class);
  }

  private void factoryCreatesUniqueInstance(Class<?> type) {
    assertNotSame(factory.newInstance(type), factory.newInstance(type));
  }

  public static class DefConstructorClass {
  }

  public static class NoDefConstructorClass {
    public NoDefConstructorClass(@SuppressWarnings("unused") String s) {
    }
  }
}
