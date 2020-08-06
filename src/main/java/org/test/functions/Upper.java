package org.test.functions;

import org.test.JpqlStringBuilder;
import org.test.operators.UnaryOperator;

public class Upper extends UnaryOperator<String> {
  Upper(String operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    stringBuilder.appendString("upper(");
    writeOperand(operand, stringBuilder);
    stringBuilder.appendString(")");
  }
}
