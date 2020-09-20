package org.pavel.jpqlbuilder.factory;

import org.junit.Test;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import static org.junit.Assert.assertNotSame;

public class DefaultCollectionInstanceFactoryTest {
  @Test
  public void newList() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(List.class);
  }

  @Test
  public void newSet() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Set.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void unsupportedCollection() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Queue.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void notCollection() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(String.class);
  }

  private void factoryCreatesUniqueInstance(Class<?> type) throws ReflectiveOperationException {
    CollectionInstanceFactory factory = new DefaultCollectionInstanceFactory();
    assertNotSame(factory.newInstance(type), factory.newInstance(type));
  }
}
