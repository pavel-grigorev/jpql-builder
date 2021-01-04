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
