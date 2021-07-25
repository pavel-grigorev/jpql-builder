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
import java.util.Calendar;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.UUID;

public class DefaultInstanceFactory implements InstanceFactory {
  private final Map<Class<?>, InstanceCreator<?>> instanceCreators = new HashMap<>();

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
    instanceCreators.put(BigDecimal.class, () -> new BigDecimal(0));
    instanceCreators.put(BigInteger.class, () -> BigInteger.valueOf(128));
    instanceCreators.put(UUID.class, UUID::randomUUID);
    instanceCreators.put(java.util.Date.class, java.util.Date::new);
    instanceCreators.put(java.sql.Date.class, () -> new java.sql.Date(0));
    instanceCreators.put(Calendar.class, GregorianCalendar::new);
    instanceCreators.put(Locale.class, () -> new Locale("en"));
    instanceCreators.put(TimeZone.class, () -> new SimpleTimeZone(0, ""));
    instanceCreators.put(Currency.class, CurrencyFactory::newInstance);
    instanceCreators.put(byte[].class, () -> new byte[0]);
  }

  public <T> void add(Class<T> type, InstanceCreator<T> instanceCreator) {
    instanceCreators.put(type, instanceCreator);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T newInstance(Class<T> type) throws ReflectiveOperationException {
    if (type.isEnum()) {
      return ObjectHelper.newInstance(type);
    }

    return (T) instanceCreators
        .getOrDefault(type, type::newInstance)
        .newInstance();
  }
}
