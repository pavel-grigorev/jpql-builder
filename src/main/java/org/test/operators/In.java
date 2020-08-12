package org.test.operators;

import org.test.JpqlStringBuilder;

import java.util.Collection;

public class In<T> extends BinaryOperator<Object, Collection<T>> {
  public In(Object operandA, Collection<T> operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);
    stringBuilder.appendString(" in ");
    writeOperand(operandB, stringBuilder);
  }
}
