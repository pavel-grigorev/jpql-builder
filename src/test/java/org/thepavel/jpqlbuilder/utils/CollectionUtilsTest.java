package org.thepavel.jpqlbuilder.utils;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class CollectionUtilsTest {
  @Test
  public void arrayToList() {
    Object[] array = new Object[3];
    array[0] = new Object();
    array[1] = new Object();
    array[2] = new Object();

    List<Object> list = CollectionUtils.toList(array);
    assertEquals(3, list.size());

    assertSame(array[0], list.get(0));
    assertSame(array[1], list.get(1));
    assertSame(array[2], list.get(2));
  }
}
