package org.pavel.jpqlbuilder.operators;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

public class NotBetween<T> extends TernaryOperator<T, T, T> {
  public NotBetween(T operandA, T operandB, T operandC) {
    super(operandA, operandB, operandC);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);
    stringWriter.appendString(" not between ");
    writeOperand(operandB, stringWriter);
    stringWriter.appendString(" and ");
    writeOperand(operandC, stringWriter);
  }
}
