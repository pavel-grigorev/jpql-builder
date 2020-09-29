package org.thepavel.jpqlbuilder.operators;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

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
