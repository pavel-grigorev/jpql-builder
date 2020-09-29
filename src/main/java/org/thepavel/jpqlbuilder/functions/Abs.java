package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Abs<T extends Number> extends JpqlFunction<T> {
  private final Object argument;

  public Abs(Number argument) {
    this.argument = argument;
  }

  public Abs(JpqlFunction<? extends Number> argument) {
    this.argument = argument;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("abs(");
    writeOperand(argument, stringWriter);
    stringWriter.appendString(")");
  }
}
