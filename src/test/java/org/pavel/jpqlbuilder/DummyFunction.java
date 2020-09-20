package org.pavel.jpqlbuilder;

import org.pavel.jpqlbuilder.functions.JpqlFunction;
import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

public class DummyFunction<T> extends JpqlFunction<T> {
  private final Object operand;

  public DummyFunction(T operand) {
    this.operand = operand;
  }

  public static <T> DummyFunction<T> dummy(T operand) {
    return new DummyFunction<>(operand);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("dummy(");
    writeOperand(operand, stringWriter);
    stringWriter.appendString(")");
  }
}
