package org.test.operators;

import org.test.JpqlStringBuilder;

public class Equal<A, B> extends BinaryOperator<A, B> {
  public Equal(A operandA, B operandB) {
    super(operandA, operandB);

    if (operandA == null) {
      throw new IllegalArgumentException("First operand must not be null");
    }
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);

    if (operandB == null) {
      stringBuilder.appendString(" is null");
    } else {
      stringBuilder.appendString(" = ");
      writeOperand(operandB, stringBuilder);
    }
  }
}
