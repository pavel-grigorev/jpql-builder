package org.test.operators;

public abstract class BinaryOperator<A, B> implements Operator {
  protected final A operandA;
  protected final B operandB;

  protected BinaryOperator(A operandA, B operandB) {
    this.operandA = operandA;
    this.operandB = operandB;
  }
}
