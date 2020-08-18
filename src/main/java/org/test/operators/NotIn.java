package org.test.operators;

import org.test.querystring.JpqlStringWriter;

import java.util.Collection;

public class NotIn<T> extends BinaryOperator<Object, Collection<T>> {
  public NotIn(Object operandA, Collection<T> operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);
    stringWriter.appendString(" not in ");
    writeOperand(operandB, stringWriter);
  }
}
