/*
 * Copyright (c) 2020 Pavel Grigorev.
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

@SuppressWarnings("UnnecessaryBoxing")
public class Primitives {
  private static final byte BYTE_VALUE = 0;
  private static final short SHORT_VALUE = 0;
  private static final int INT_VALUE = 0;
  private static final long LONG_VALUE = 0;
  private static final float FLOAT_VALUE = 0;
  private static final double DOUBLE_VALUE = 0;
  private static final boolean BOOLEAN_VALUE = false;
  private static final char CHAR_VALUE = '0';

  static Byte newByte() {
    return new Byte(BYTE_VALUE);
  }

  static Short newShort() {
    return new Short(SHORT_VALUE);
  }

  static Integer newInteger() {
    return new Integer(INT_VALUE);
  }

  static Long newLong() {
    return new Long(LONG_VALUE);
  }

  static Float newFloat() {
    return new Float(FLOAT_VALUE);
  }

  static Double newDouble() {
    return new Double(DOUBLE_VALUE);
  }

  @SuppressWarnings("BooleanConstructorCall")
  static Boolean newBoolean() {
    return new Boolean(BOOLEAN_VALUE);
  }

  static Character newCharacter() {
    return new Character(CHAR_VALUE);
  }

  static Object get(Class<?> type) {
    switch (type.getName()) {
      case "byte": return newByte();
      case "short": return newShort();
      case "int": return newInteger();
      case "long": return newLong();
      case "float": return newFloat();
      case "double": return newDouble();
      case "boolean": return newBoolean();
      case "char": return newCharacter();
      default: return null;
    }
  }
}
