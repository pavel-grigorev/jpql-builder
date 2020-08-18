package org.test.operators;

import org.test.querystring.JpqlStringWriter;

public class DummyJpqlStringWriter implements JpqlStringWriter {
  private final StringBuilder builder = new StringBuilder();

  @Override
  public void appendString(String string) {
    builder.append(string);
  }

  @Override
  public void appendValue(Object value) {
    builder.append(value);
  }

  @Override
  public String toString() {
    return builder.toString();
  }
}
