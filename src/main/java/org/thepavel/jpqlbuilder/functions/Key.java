package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.Map;

public class Key<T> extends JpqlFunction<T> {
  private final Map<T, ?> argument;

  public Key(Map<T, ?> argument) {
    this.argument = argument;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("key(");
    writeOperand(argument, stringWriter);
    stringWriter.appendString(")");
  }
}
