package org.test.factory;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import static org.junit.Assert.assertNotSame;

public class DefaultCollectionInstanceFactoryTest {
  @Test
  public void newList() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(List.class), factory.newInstance(List.class));
  }

  @Test
  public void newSet() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Set.class), factory.newInstance(Set.class));
  }

  @Test(expected = IllegalArgumentException.class)
  public void unsupportedCollection() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(Queue.class), factory.newInstance(Queue.class));
  }

  @Test(expected = IllegalArgumentException.class)
  public void notCollection() throws ReflectiveOperationException {
    assertNotSame(factory.newInstance(String.class), factory.newInstance(String.class));
  }

  private CollectionInstanceFactory factory;

  @Before
  public void setup() {
    factory = new DefaultCollectionInstanceFactory();
  }
}
