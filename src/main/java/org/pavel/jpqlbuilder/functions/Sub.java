package org.pavel.jpqlbuilder.functions;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Sub<T extends Number> extends JpqlFunction<T> {
  private final Object argument1;
  private final Object argument2;

  public Sub(Number argument1, Number argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Sub(JpqlFunction<? extends Number> argument1, Number argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Sub(Number argument1, JpqlFunction<? extends Number> argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Sub(JpqlFunction<? extends Number> argument1, JpqlFunction<? extends Number> argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(argument1, stringWriter);
    stringWriter.appendString(" - ");
    appendArgument2(stringWriter);
  }

  private void appendArgument2(JpqlStringWriter stringWriter) {
    boolean shouldWrap = shouldWrap();
    if (shouldWrap) {
      stringWriter.appendString("(");
    }
    writeOperand(argument2, stringWriter);
    if (shouldWrap) {
      stringWriter.appendString(")");
    }
  }

  private boolean shouldWrap() {
    Class<?> type = argument2.getClass();
    return type == Add.class || type == Sub.class;
  }
}
