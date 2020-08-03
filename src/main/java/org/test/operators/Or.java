package org.test.operators;

import org.test.JpqlStringBuilder;

public class Or<A, B> extends BinaryOperator<A, B> {
  public Or(A operandA, B operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);
    stringBuilder.appendString(" or ");
    writeOperand(operandB, stringBuilder);
  }
}
