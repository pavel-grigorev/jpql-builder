package org.test.operators;

import org.test.JpqlStringBuilder;

public class LessThan<T> extends BinaryOperator<T, T> {
  public LessThan(T operandA, T operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);
    stringBuilder.appendString(" < ");
    writeOperand(operandB, stringBuilder);
  }
}
