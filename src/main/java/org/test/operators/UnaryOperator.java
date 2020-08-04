package org.test.operators;

abstract class UnaryOperator<T> extends Operator {
  final T operand;

  UnaryOperator(T operand) {
    this.operand = operand;
  }
}
