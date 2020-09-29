package org.thepavel.jpqlbuilder.operators;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

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
