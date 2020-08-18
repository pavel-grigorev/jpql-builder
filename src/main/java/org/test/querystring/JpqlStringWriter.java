package org.test.querystring;

public interface JpqlStringWriter {
  void appendString(String string);
  void appendValue(Object value);
}
