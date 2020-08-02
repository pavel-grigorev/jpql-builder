package org.test.operators;

import org.test.JpqlBuilder;

public class IsNotNull<T> extends UnaryOperator<T> {
  public IsNotNull(T operand) {
    super(operand);

    if (operand == null) {
      throw new IllegalArgumentException("Operand must not be null");
    }
  }

  @Override
  public void writeTo(JpqlBuilder<?> jpqlBuilder) {
    writeOperand(operand, jpqlBuilder);
    jpqlBuilder.appendString(" is not null");
  }
}
