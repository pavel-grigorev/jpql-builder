package org.thepavel.jpqlbuilder.factory;

import org.junit.Test;

import java.util.Map;
import java.util.SortedMap;

import static org.junit.Assert.assertNotSame;

public class DefaultMapInstanceFactoryTest {
  @Test
  public void newMap() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Map.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void unsupportedMap() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(SortedMap.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void notMap() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(String.class);
  }

  private void factoryCreatesUniqueInstance(Class<?> type) throws ReflectiveOperationException {
    MapInstanceFactory factory = new DefaultMapInstanceFactory();
    assertNotSame(factory.newInstance(type), factory.newInstance(type));
  }
}
