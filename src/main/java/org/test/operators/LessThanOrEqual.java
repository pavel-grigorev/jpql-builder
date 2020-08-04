package org.test.operators;

import org.test.JpqlStringBuilder;

public class LessThanOrEqual<T> extends BinaryOperator<T, T> {
  public LessThanOrEqual(T operandA, T operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);
    stringBuilder.appendString(" <= ");
    writeOperand(operandB, stringBuilder);
  }
}
