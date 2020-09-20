package org.pavel.jpqlbuilder.operators;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

public class IsNotNull<T> extends UnaryOperator<T> {
  public IsNotNull(T operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operand, stringWriter);
    stringWriter.appendString(" is not null");
  }
}
