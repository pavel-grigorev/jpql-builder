package org.test;

import org.test.operators.Operator;
import org.test.querystring.JpqlStringWriter;

public class DummyJpqlStringWriter implements JpqlStringWriter {
  private final StringBuilder builder = new StringBuilder();

  @Override
  public void appendString(String string) {
    builder.append(string);
  }

  @Override
  public void appendValue(Object value) {
    if (value instanceof Class) {
      builder.append(((Class<?>) value).getSimpleName());
      return;
    }
    builder.append(value);
  }

  @Override
  public String toString() {
    return builder.toString();
  }

  public static String asString(Operator operator) {
    JpqlStringWriter stringWriter = new DummyJpqlStringWriter();
    operator.writeTo(stringWriter);
    return stringWriter.toString();
  }
}
