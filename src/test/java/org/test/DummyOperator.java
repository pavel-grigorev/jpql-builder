package org.test;

import org.test.operators.UnaryOperator;
import org.test.querystring.JpqlStringWriter;

public class DummyOperator<T> extends UnaryOperator<T> {
  public DummyOperator(T operand) {
    super(operand);
  }

  public static <T> DummyOperator<T> dummy(T operand) {
    return new DummyOperator<>(operand);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("dummy(");
    writeOperand(operand, stringWriter);
    stringWriter.appendString(")");
  }
}
