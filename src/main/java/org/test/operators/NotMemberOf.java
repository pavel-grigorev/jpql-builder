package org.test.operators;

import org.test.querystring.JpqlStringWriter;

import java.util.Collection;

public class NotMemberOf<T> extends BinaryOperator<Object, Collection<T>> {
  public NotMemberOf(Object operandA, Collection<T> operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);
    stringWriter.appendString(" not member of ");
    writeOperand(operandB, stringWriter);
  }
}
