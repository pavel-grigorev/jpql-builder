package org.test.operators;

import org.test.JpqlStringBuilder;

public class IsNotNull<T> extends UnaryOperator<T> {
  public IsNotNull(T operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operand, stringBuilder);
    stringBuilder.appendString(" is not null");
  }
}
