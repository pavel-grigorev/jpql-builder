package org.test.operators;

public class NotLike extends Like {
  public NotLike(String operandA, String operandB) {
    super(operandA, operandB);
  }

  public NotLike(String operandA, String operandB, String escapeChar) {
    super(operandA, operandB, escapeChar);
  }

  @Override
  String getLikeOperator() {
    return " not like ";
  }
}
