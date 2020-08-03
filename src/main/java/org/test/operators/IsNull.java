package org.test.operators;

import org.test.JpqlStringBuilder;

public class IsNull<T> extends UnaryOperator<T> {
  public IsNull(T operand) {
    super(operand);

    if (operand == null) {
      throw new IllegalArgumentException("Operand must not be null");
    }
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operand, stringBuilder);
    stringBuilder.appendString(" is null");
  }
}
