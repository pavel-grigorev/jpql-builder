package org.test.operators;

abstract class TernaryOperator<A, B, C> extends Operator {
  final A operandA;
  final B operandB;
  final C operandC;

  TernaryOperator(A operandA, B operandB, C operandC) {
    this.operandA = operandA;
    this.operandB = operandB;
    this.operandC = operandC;
  }
}
