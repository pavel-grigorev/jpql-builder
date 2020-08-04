package org.test.operators;

import org.test.JpqlStringBuilder;

public class Where extends UnaryOperator<Operator> {
  public Where(Operator operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    stringBuilder.appendString(" where ");
    writeOperand(operand, stringBuilder);
  }
}
