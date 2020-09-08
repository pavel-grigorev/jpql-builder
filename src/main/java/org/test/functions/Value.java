package org.test.functions;

import org.test.querystring.JpqlStringWriter;

import java.util.Map;

public class Value<T> extends JpqlFunction<T> {
  private final Map<?, T> argument;

  public Value(Map<?, T> argument) {
    this.argument = argument;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("value(");
    writeOperand(argument, stringWriter);
    stringWriter.appendString(")");
  }
}
