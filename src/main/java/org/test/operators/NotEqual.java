package org.test.operators;

import org.test.JpqlStringBuilder;

public class NotEqual<T> extends BinaryOperator<T, T> {
  public NotEqual(T operandA, T operandB) {
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
