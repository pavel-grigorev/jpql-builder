package org.test.query;

import org.test.JpqlStringBuilder;
import org.test.operators.Operator;
import org.test.operators.UnaryOperator;

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
