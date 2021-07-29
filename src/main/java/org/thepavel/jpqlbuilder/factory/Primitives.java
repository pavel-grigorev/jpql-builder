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

class Primitives {
  private Primitives() {
  }

  static Byte newByte() {
    final byte x = 0;
    return new Byte(x);
  }

  static Short newShort() {
    final short x = 0;
    return new Short(x);
  }

  static Integer newInteger() {
    return new Integer(0);
  }

  static Long newLong() {
    return new Long(0);
  }

  static Float newFloat() {
    return new Float(0);
  }

  static Double newDouble() {
    return new Double(0);
  }

  static Boolean newBoolean() {
    return new Boolean(false);
  }

  static Character newCharacter() {
    return new Character('0');
  }
}
