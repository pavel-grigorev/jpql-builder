package org.thepavel.jpqlbuilder.factory;

import java.lang.reflect.Constructor;
import java.util.Currency;

class CurrencyFactory {
  private CurrencyFactory() {
  }

  static Currency newInstance() throws ReflectiveOperationException {
    Constructor<Currency> c = Currency.class.getDeclaredConstructor(String.class, int.class, int.class);
    c.setAccessible(true);
    return c.newInstance("USD", 0, 0);
  }
}
