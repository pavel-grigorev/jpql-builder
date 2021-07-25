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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.function.Supplier;

public class DefaultInstanceFactory implements InstanceFactory {
  private final Map<Class<?>, Supplier<?>> instanceCreators = new HashMap<>();

  public DefaultInstanceFactory() {
    initInstanceCreators();
  }

  private void initInstanceCreators() {
    instanceCreators.put(Calendar.class, GregorianCalendar::new);
    instanceCreators.put(TimeZone.class, () -> new SimpleTimeZone(0, ""));
    instanceCreators.put(byte[].class, () -> new byte[0]);
  }

  public <T> void add(Class<T> type, Supplier<T> instanceCreator) {
    instanceCreators.put(type, instanceCreator);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T newInstance(Class<T> type) throws ReflectiveOperationException {
    if (type.isEnum()) {
      return ObjectHelper.newInstance(type);
    }
    Supplier<?> instanceCreator = instanceCreators.get(type);
    return instanceCreator != null ? (T) instanceCreator.get() : ObjectHelper.newInstance(type);
  }
}
