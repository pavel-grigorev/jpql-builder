package org.test.operators;

import org.test.JpqlBuilder;

public class And<A, B> extends BinaryOperator<A, B> {
  public And(A operandA, B operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlBuilder<?> jpqlBuilder) {
    writeOperand(operandA, jpqlBuilder);
    jpqlBuilder.appendString(" and ");
    writeOperand(operandB, jpqlBuilder);
  }
}
