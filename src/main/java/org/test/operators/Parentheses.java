package org.test.operators;

import org.test.JpqlStringBuilder;

public class Parentheses extends UnaryOperator<Operator> {
  public Parentheses(Operator operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    stringBuilder.appendString("(");
    writeOperand(operand, stringBuilder);
    stringBuilder.appendString(")");
  }
}
