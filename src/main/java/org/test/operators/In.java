package org.test.operators;

import org.test.querystring.JpqlStringWriter;

import java.util.Collection;

public class In<T> extends BinaryOperator<Object, Collection<T>> {
  public In(Object operandA, Collection<T> operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);
    stringWriter.appendString(" in ");
    writeOperand(operandB, stringWriter);
  }
}
