package org.pavel.jpqlbuilder.factory;

import org.junit.Test;

import static org.junit.Assert.assertNotSame;

public class CurrencyFactoryTest {
  @Test
  public void test() throws ReflectiveOperationException {
    assertNotSame(CurrencyFactory.newInstance(), CurrencyFactory.newInstance());
  }
}
