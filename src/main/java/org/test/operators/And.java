package org.test.operators;

import org.test.JpqlStringBuilder;

public class And<A, B> extends BinaryOperator<A, B> {
  public And(A operandA, B operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);
    stringBuilder.appendString(" and ");
    writeOperand(operandB, stringBuilder);
  }
}
