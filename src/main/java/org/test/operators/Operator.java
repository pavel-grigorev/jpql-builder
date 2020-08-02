package org.test.operators;

import org.test.JpqlBuilder;

public abstract class Operator {
  public abstract void writeTo(JpqlBuilder<?> jpqlBuilder);

  static void writeOperand(Object operand, JpqlBuilder<?> jpqlBuilder) {
    if (operand instanceof Operator) {
      ((Operator) operand).writeTo(jpqlBuilder);
    } else {
      jpqlBuilder.appendValue(operand);
    }
  }
}
