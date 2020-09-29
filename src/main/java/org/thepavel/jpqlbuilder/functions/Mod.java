package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Mod<T extends Number> extends JpqlFunction<T> {
  private final Div<T> argument;

  public Mod(Div<T> argument) {
    this.argument = argument;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("mod(");
    writeOperand(argument, stringWriter);
    stringWriter.appendString(")");
  }
}
