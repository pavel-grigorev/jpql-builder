package org.test.operators;

import org.test.JpqlStringBuilder;

public class NotEqual<A, B> extends BinaryOperator<A, B> {
  public NotEqual(A operandA, B operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);

    if (operandB == null) {
      stringBuilder.appendString(" is not null");
    } else {
      stringBuilder.appendString(" <> ");
      writeOperand(operandB, stringBuilder);
    }
  }
}
