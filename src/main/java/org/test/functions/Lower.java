package org.test.functions;

import org.test.JpqlStringBuilder;
import org.test.operators.UnaryOperator;

public class Lower extends UnaryOperator<String> {
  Lower(String operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    stringBuilder.appendString("lower(");
    writeOperand(operand, stringBuilder);
    stringBuilder.appendString(")");
  }
}
