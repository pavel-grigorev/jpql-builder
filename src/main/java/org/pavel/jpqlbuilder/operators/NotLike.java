package org.pavel.jpqlbuilder.operators;

public class NotLike extends Like {
  public NotLike(Object operandA, Object operandB) {
    super(operandA, operandB);
  }

  public NotLike(Object operandA, Object operandB, String escapeChar) {
    super(operandA, operandB, escapeChar);
  }

  @Override
  String getLikeOperator() {
    return " not like ";
  }
}
