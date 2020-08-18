package org.test.operators;

import org.test.querystring.JpqlStringWriter;

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
