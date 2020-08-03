package org.test.operators;

import org.test.JpqlStringBuilder;

public class Not extends UnaryOperator<Operator> {
  public Not(Operator operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    stringBuilder.appendString("not (");
    operand.writeTo(stringBuilder);
    stringBuilder.appendString(")");
  }
}
