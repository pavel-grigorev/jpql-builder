package org.test.functions;

import org.test.querystring.JpqlStringWriter;

public class Multi<T extends Number> extends JpqlFunction<T> {
  private final Object argument1;
  private final Object argument2;

  public Multi(Number argument1, Number argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Multi(JpqlFunction<? extends Number> argument1, Number argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Multi(Number argument1, JpqlFunction<? extends Number> argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Multi(JpqlFunction<? extends Number> argument1, JpqlFunction<? extends Number> argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    appendArgument(argument1, stringWriter);
    stringWriter.appendString(getOperator());
    appendArgument(argument2, stringWriter);
  }

  private void appendArgument(Object argument, JpqlStringWriter stringWriter) {
    boolean shouldWrap = shouldWrap(argument.getClass());
    if (shouldWrap) {
      stringWriter.appendString("(");
    }
    writeOperand(argument, stringWriter);
    if (shouldWrap) {
      stringWriter.appendString(")");
    }
  }

  boolean shouldWrap(Class<?> type) {
    return type == Add.class || type == Sub.class || type == Div.class;
  }

  String getOperator() {
    return " * ";
  }
}
