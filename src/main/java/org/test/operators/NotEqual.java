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
    } else if (Boolean.TRUE.equals(operandB)) {
      stringBuilder.appendString(" is false");
    } else if (Boolean.FALSE.equals(operandB)) {
      stringBuilder.appendString(" is true");
    } else {
      stringBuilder.appendString(" <> ");
      writeOperand(operandB, stringBuilder);
    }
  }
}
