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

import java.lang.reflect.Constructor;
import java.util.Currency;

class CurrencyFactory {
  private CurrencyFactory() {
  }

  static Currency newCurrency() {
    try {
      return getConstructor().newInstance("", 0, 0);
    } catch (ReflectiveOperationException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  private static Constructor<Currency> getConstructor() throws NoSuchMethodException {
    Constructor<Currency> c = Currency.class.getDeclaredConstructor(String.class, int.class, int.class);
    c.setAccessible(true);
    return c;
  }
}
