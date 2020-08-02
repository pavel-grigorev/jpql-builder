package org.test.operators;

public abstract class BinaryOperator<A, B> extends Operator {
  final A operandA;
  final B operandB;

  BinaryOperator(A operandA, B operandB) {
    this.operandA = operandA;
    this.operandB = operandB;
  }
}
