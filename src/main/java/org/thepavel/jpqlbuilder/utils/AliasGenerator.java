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

public class AliasGenerator {
  private static final int MAX_LENGTH = 4;
  private static final char MIN_CHAR = 'a';
  private static final char MAX_CHAR = 'z';

  /**
   * Min alias is "a".
   * Max alias is "zzzz".
   */
  private final char[] name = new char[MAX_LENGTH];

  public void reset() {
    for (int i = 0; i < MAX_LENGTH; i++) {
      name[i] = 0;
    }
  }

  public String next() {
    incrementAt(0);
    return buildAlias();
  }

  private void incrementAt(int position) {
    if (position == MAX_LENGTH) {
      throw new IllegalStateException("Too many aliases");
    }
    char c = name[position];
    if (c == 0) {
      name[position] = MIN_CHAR;
      return;
    }
    if (c == MAX_CHAR) {
      incrementAt(position + 1);
      name[position] = MIN_CHAR;
      return;
    }
    name[position] = ++c;
  }

  private String buildAlias() {
    int position = getFirstNonZeroPosition();

    // name is just one char
    if (position == 0) {
      return String.valueOf(name[0]);
    }

    StringBuilder builder = new StringBuilder();
    for (int i = position; i >= 0; i--) {
      builder.append(name[i]);
    }
    return builder.toString();
  }

  private int getFirstNonZeroPosition() {
    for (int i = MAX_LENGTH - 1; i >= 0; i--) {
      if (name[i] > 0) {
        return i;
      }
    }
    return 0;
  }
}
