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

package org.thepavel.jpqlbuilder.factory;

import org.junit.Test;

import static org.junit.Assert.assertNotSame;

public class PrimitivesTest {
  @Test
  public void newByte() {
    assertNotSame(Primitives.newByte(), Primitives.newByte());
  }

  @Test
  public void newShort() {
    assertNotSame(Primitives.newShort(), Primitives.newShort());
  }

  @Test
  public void newInteger() {
    assertNotSame(Primitives.newInteger(), Primitives.newInteger());
  }

  @Test
  public void newLong() {
    assertNotSame(Primitives.newLong(), Primitives.newLong());
  }

  @Test
  public void newFloat() {
    assertNotSame(Primitives.newFloat(), Primitives.newFloat());
  }

  @Test
  public void newDouble() {
    assertNotSame(Primitives.newDouble(), Primitives.newDouble());
  }

  @Test
  public void newBoolean() {
    assertNotSame(Primitives.newBoolean(), Primitives.newBoolean());
  }

  @Test
  public void newCharacter() {
    assertNotSame(Primitives.newCharacter(), Primitives.newCharacter());
  }
}
