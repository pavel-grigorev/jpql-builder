package org.test.operators;

import org.test.JpqlStringBuilder;

public class NotBetween<T> extends TernaryOperator<T, T, T> {
  public NotBetween(T operandA, T operandB, T operandC) {
    super(operandA, operandB, operandC);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);
    stringBuilder.appendString(" not between ");
    writeOperand(operandB, stringBuilder);
    stringBuilder.appendString(" and ");
    writeOperand(operandC, stringBuilder);
  }
}
