package org.test.operators;

import org.test.JpqlStringBuilder;

public class Like extends BinaryOperator<String, String> {
  public Like(String operandA, String operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);
    stringBuilder.appendString(" like ");
    writeOperand(operandB, stringBuilder);
  }
}
