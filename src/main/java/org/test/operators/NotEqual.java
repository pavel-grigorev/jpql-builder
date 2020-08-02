package org.test.operators;

import org.test.JpqlBuilder;

public class NotEqual<A, B> extends BinaryOperator<A, B> {
  public NotEqual(A operandA, B operandB) {
    super(operandA, operandB);

    if (operandA == null) {
      throw new IllegalArgumentException("First operand must not be null");
    }
  }

  @Override
  public void writeTo(JpqlBuilder<?> jpqlBuilder) {
    writeOperand(operandA, jpqlBuilder);

    if (operandB == null) {
      jpqlBuilder.appendString(" is not null");
    } else {
      jpqlBuilder.appendString(" <> ");
      writeOperand(operandB, jpqlBuilder);
    }
  }
}
