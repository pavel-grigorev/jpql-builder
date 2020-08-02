package org.test.operators;

import org.test.JpqlBuilder;

public class Not extends UnaryOperator<Operator> {
  public Not(Operator operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlBuilder<?> jpqlBuilder) {
    jpqlBuilder.appendString("not (");
    operand.writeTo(jpqlBuilder);
    jpqlBuilder.appendString(")");
  }
}
