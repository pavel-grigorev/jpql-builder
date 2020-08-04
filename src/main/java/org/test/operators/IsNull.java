package org.test.operators;

import org.test.JpqlStringBuilder;

public class IsNull<T> extends UnaryOperator<T> {
  public IsNull(T operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operand, stringBuilder);
    stringBuilder.appendString(" is null");
  }
}
