package org.test.operators;

import org.test.JpqlBuilder;

public class Equal<A, B> extends BinaryOperator<A, B> {
  public Equal(A operandA, B operandB) {
    super(operandA, operandB);

    if (operandA == null) {
      throw new IllegalArgumentException("First operand must not be null");
    }
  }

  @Override
  public void writeTo(JpqlBuilder<?> jpqlBuilder) {
    writeOperand(operandA, jpqlBuilder);

    if (operandB == null) {
      jpqlBuilder.appendString(" is null");
    } else {
      jpqlBuilder.appendString(" = ");
      writeOperand(operandB, jpqlBuilder);
    }
  }
}
