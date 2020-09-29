package org.thepavel.jpqlbuilder.operators;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class LessThanOrEqual<T> extends BinaryOperator<T, T> {
  public LessThanOrEqual(T operandA, T operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);
    stringWriter.appendString(" <= ");
    writeOperand(operandB, stringWriter);
  }
}
