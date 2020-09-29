package org.thepavel.jpqlbuilder.operators;

public abstract class TernaryOperator<A, B, C> implements Operator {
  protected final A operandA;
  protected final B operandB;
  protected final C operandC;

  protected TernaryOperator(A operandA, B operandB, C operandC) {
    this.operandA = operandA;
    this.operandB = operandB;
    this.operandC = operandC;
  }
}
