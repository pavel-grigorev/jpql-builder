package org.test.operators;

import org.test.querystring.JpqlStringWriter;

public class Between<T> extends TernaryOperator<T, T, T> {
  public Between(T operandA, T operandB, T operandC) {
    super(operandA, operandB, operandC);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);
    stringWriter.appendString(" between ");
    writeOperand(operandB, stringWriter);
    stringWriter.appendString(" and ");
    writeOperand(operandC, stringWriter);
  }
}
