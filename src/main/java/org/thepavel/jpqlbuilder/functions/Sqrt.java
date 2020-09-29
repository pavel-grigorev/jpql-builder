package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Sqrt<T extends Number> extends JpqlFunction<T> {
  private final Object argument;

  public Sqrt(Number argument) {
    this.argument = argument;
  }

  public Sqrt(JpqlFunction<? extends Number> argument) {
    this.argument = argument;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("sqrt(");
    writeOperand(argument, stringWriter);
    stringWriter.appendString(")");
  }
}
