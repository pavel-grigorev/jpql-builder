package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Nullif<T> extends JpqlFunction<T> {
  private final Object argument1;
  private final Object argument2;

  public Nullif(T argument1, T argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Nullif(JpqlFunction<T> argument1, T argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Nullif(T argument1, JpqlFunction<T> argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Nullif(JpqlFunction<T> argument1, JpqlFunction<T> argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("nullif(");
    writeOperand(argument1, stringWriter);
    stringWriter.appendString(", ");
    writeOperand(argument2, stringWriter);
    stringWriter.appendString(")");
  }
}
