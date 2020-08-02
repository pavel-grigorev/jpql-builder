package org.test.operators;

import org.test.JpqlBuilder;

public class Like extends BinaryOperator<String, String> {
  public Like(String operandA, String operandB) {
    super(operandA, operandB);

    if (operandA == null || operandB == null) {
      throw new IllegalArgumentException("Operand must not be null");
    }
  }

  @Override
  public void writeTo(JpqlBuilder<?> jpqlBuilder) {
    writeOperand(operandA, jpqlBuilder);
    jpqlBuilder.appendString(" like ");
    writeOperand(operandB, jpqlBuilder);
  }
}
