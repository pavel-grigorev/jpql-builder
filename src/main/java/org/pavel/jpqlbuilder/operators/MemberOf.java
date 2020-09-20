package org.pavel.jpqlbuilder.operators;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.Collection;

public class MemberOf<T> extends BinaryOperator<Object, Collection<T>> {
  public MemberOf(Object operandA, Collection<T> operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);
    stringWriter.appendString(" member of ");
    writeOperand(operandB, stringWriter);
  }
}
