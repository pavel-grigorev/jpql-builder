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

package org.thepavel.jpqlbuilder.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class PairTest {
  @Test
  public void leftAndRight() {
    Object a = new Object();
    Object b = new Object();

    Pair<Object, Object> pair = Pair.of(a, b);

    assertNotNull(pair);
    assertSame(a, pair.getLeft());
    assertSame(b, pair.getRight());
  }

  @Test
  public void keyAndValue() {
    Object a = new Object();
    Object b = new Object();

    Pair<Object, Object> pair = Pair.of(a, b);

    assertNotNull(pair);
    assertSame(a, pair.getKey());
    assertSame(b, pair.getValue());
  }

  @Test
  public void testEquals() {
    testEquals("a", "b");
    testEquals(null, "b");
    testEquals("a", null);
    testEquals(null, null);
  }

  @Test
  public void testHashCode() {
    testHashCode("a", "b");
    testHashCode(null, "b");
    testHashCode("a", null);
    testHashCode(null, null);
  }

  private static void testEquals(String a, String b) {
    Pair<String, String> pair1 = Pair.of(a, b);
    Pair<String, String> pair2 = Pair.of(a, b);

    assertNotSame(pair1, pair2);
    assertEquals(pair1, pair2);
  }

  private static void testHashCode(String a, String b) {
    Pair<String, String> pair1 = Pair.of(a, b);
    Pair<String, String> pair2 = Pair.of(a, b);

    assertNotSame(pair1, pair2);
    assertEquals(pair1.hashCode(), pair2.hashCode());
  }
}