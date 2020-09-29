package org.thepavel.jpqlbuilder.factory;

import org.junit.Test;
import org.thepavel.jpqlbuilder.model.Status;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EnumFactoryTest {
  @Test
  public void returnsUniqueInstance() {
    Object instance = new EnumFactory().newInstance(Status.class);

    assertNotNull(instance);
    assertTrue(instance instanceof Status);
    assertTrue(Arrays.stream(Status.values()).noneMatch(v -> v == instance));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nonEnumClass() {
    new EnumFactory().newInstance(String.class);
  }
}
