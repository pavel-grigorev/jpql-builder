package org.test;

import org.test.functions.JpqlFunction;
import org.test.querystring.JpqlStringWriter;

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
