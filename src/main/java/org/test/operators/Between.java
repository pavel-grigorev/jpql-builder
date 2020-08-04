package org.test.operators;

import org.test.JpqlStringBuilder;

public class Between<T> extends TernaryOperator<T, T, T> {
  public Between(T operandA, T operandB, T operandC) {
    super(operandA, operandB, operandC);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);
    stringBuilder.appendString(" between ");
    writeOperand(operandB, stringBuilder);
    stringBuilder.appendString(" and ");
    writeOperand(operandC, stringBuilder);
  }
}
