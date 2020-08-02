package org.test.operators;

import org.test.JpqlBuilder;

public class Or<A, B> extends BinaryOperator<A, B> {
  public Or(A operandA, B operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlBuilder<?> jpqlBuilder) {
    writeOperand(operandA, jpqlBuilder);
    jpqlBuilder.appendString(" or ");
    writeOperand(operandB, jpqlBuilder);
  }
}
