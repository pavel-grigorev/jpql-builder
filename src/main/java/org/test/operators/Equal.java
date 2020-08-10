package org.test.operators;

import org.test.JpqlStringBuilder;

public class Equal<T> extends BinaryOperator<T, T> {
  public Equal(T operandA, T operandB) {
    super(operandA, operandB);
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
