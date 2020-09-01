package org.test.functions;

import org.test.querystring.JpqlStringWriter;

public class Add<T extends Number> extends JpqlFunction<T> {
  private final Object argument1;
  private final Object argument2;

  public Add(Number argument1, Number argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Add(JpqlFunction<? extends Number> argument1, Number argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Add(Number argument1, JpqlFunction<? extends Number> argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Add(JpqlFunction<? extends Number> argument1, JpqlFunction<? extends Number> argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(argument1, stringWriter);
    stringWriter.appendString(getOperator());
    writeOperand(argument2, stringWriter);
  }

  String getOperator() {
    return " + ";
  }
}
