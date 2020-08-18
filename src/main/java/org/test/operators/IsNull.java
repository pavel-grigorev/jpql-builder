package org.test.operators;

import org.test.querystring.JpqlStringWriter;

public class IsNull<T> extends UnaryOperator<T> {
  public IsNull(T operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operand, stringWriter);
    stringWriter.appendString(" is null");
  }
}
