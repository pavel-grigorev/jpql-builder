package org.test.operators;

import org.test.JpqlStringBuilder;

public abstract class Operator {
  public abstract void writeTo(JpqlStringBuilder<?> stringBuilder);

  protected static void writeOperand(Object operand, JpqlStringBuilder<?> stringBuilder) {
    if (operand instanceof Operator) {
      ((Operator) operand).writeTo(stringBuilder);
    } else {
      stringBuilder.appendValue(operand);
    }
  }
}
