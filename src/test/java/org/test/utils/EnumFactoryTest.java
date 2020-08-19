package org.test.utils;

import org.junit.Test;
import org.test.entities.Status;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EnumFactoryTest {
  @Test
  public void returnsUniqueInstance() {
    Object instance = EnumFactory.newInstance(Status.class);

    assertNotNull(instance);
    assertTrue(instance instanceof Status);
    assertTrue(Arrays.stream(Status.values()).noneMatch(v -> v == instance));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nonEnumClass() {
    EnumFactory.newInstance(String.class);
  }
}
