package org.test;

class AliasGenerator {
  private static final int MAX_LENGTH = 4;
  private static final char MIN_CHAR = 'a';
  private static final char MAX_CHAR = 'z';

  /**
   * Min alias is "a".
   * Max alias is "zzzz".
   */
  private final char[] name = new char[MAX_LENGTH];

  void reset() {
    for (int i = 0; i < MAX_LENGTH; i++) {
      name[i] = 0;
    }
  }

  String next() {
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
