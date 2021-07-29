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

import org.thepavel.jpqlbuilder.utils.ObjectHelper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.UUID;
import java.util.function.Supplier;

public class DefaultInstanceFactory implements InstanceFactory {
  private final Map<Class<?>, Supplier<?>> instanceCreators = new HashMap<>();

  public DefaultInstanceFactory() {
    initInstanceCreators();
  }

  private void initInstanceCreators() {
    instanceCreators.put(Byte.class, Primitives::newByte);
    instanceCreators.put(Short.class, Primitives::newShort);
    instanceCreators.put(Integer.class, Primitives::newInteger);
    instanceCreators.put(Long.class, Primitives::newLong);
    instanceCreators.put(Float.class, Primitives::newFloat);
    instanceCreators.put(Double.class, Primitives::newDouble);
    instanceCreators.put(Boolean.class, Primitives::newBoolean);
    instanceCreators.put(Character.class, Primitives::newCharacter);
    instanceCreators.put(String.class, String::new);
    instanceCreators.put(BigDecimal.class, () -> new BigDecimal(0));
    instanceCreators.put(BigInteger.class, () -> BigInteger.valueOf(100));
    instanceCreators.put(UUID.class, UUID::randomUUID);
    instanceCreators.put(java.util.Date.class, java.util.Date::new);
    instanceCreators.put(java.sql.Date.class, () -> new java.sql.Date(0));
    instanceCreators.put(LocalDate.class, () -> LocalDate.of(1970, 1, 1));
    instanceCreators.put(LocalDateTime.class, () -> LocalDateTime.of(LocalDate.MIN, LocalTime.MIN));
    instanceCreators.put(LocalTime.class, () -> LocalTime.of(1, 1)); // when minute is 0 returns a cached value
    instanceCreators.put(Instant.class, () -> Instant.ofEpochMilli(1)); // returns a cached value on 0
    instanceCreators.put(Calendar.class, GregorianCalendar::new);
    instanceCreators.put(Locale.class, () -> new Locale("en"));
    instanceCreators.put(TimeZone.class, () -> new SimpleTimeZone(0, ""));
    instanceCreators.put(Currency.class, CurrencyFactory::newCurrency);
    instanceCreators.put(byte[].class, () -> new byte[0]);
    instanceCreators.put(char[].class, () -> new char[0]);
  }

  public <T> void add(Class<T> type, Supplier<T> instanceCreator) {
    instanceCreators.put(type, instanceCreator);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T newInstance(Class<T> type) {
    if (type.isEnum()) {
      return ObjectHelper.newInstanceWithoutConstructor(type);
    }
    Supplier<?> instanceCreator = instanceCreators.get(type);
    if (instanceCreator != null) {
      return (T) instanceCreator.get();
    }
    try {
      return type.newInstance();
    } catch (ReflectiveOperationException e) {
      return ObjectHelper.newInstanceWithoutConstructor(type);
    }
  }
}
