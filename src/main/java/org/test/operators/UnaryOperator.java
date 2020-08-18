package org.test.operators;

public abstract class UnaryOperator<T> implements Operator {
  protected final T operand;

  protected UnaryOperator(T operand) {
    this.operand = operand;
  }
}
