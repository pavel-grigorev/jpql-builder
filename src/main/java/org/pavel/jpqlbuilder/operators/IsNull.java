package org.pavel.jpqlbuilder.operators;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

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
