package org.test.operators;

import org.test.JpqlStringBuilder;

import java.util.Collection;

public class NotIn<T> extends BinaryOperator<T, Collection<T>> {
  public NotIn(T operandA, Collection<T> operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);
    stringBuilder.appendString(" not in ");
    writeOperand(operandB, stringBuilder);
  }
}
