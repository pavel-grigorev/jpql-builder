package org.test.operators;

import org.test.JpqlBuilder;

public class Parentheses extends UnaryOperator<Operator> {
  public Parentheses(Operator operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlBuilder<?> jpqlBuilder) {
    jpqlBuilder.appendString("(");
    writeOperand(operand, jpqlBuilder);
    jpqlBuilder.appendString(")");
  }
}
