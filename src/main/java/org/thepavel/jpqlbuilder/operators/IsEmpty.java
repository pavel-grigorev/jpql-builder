package org.thepavel.jpqlbuilder.operators;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.Collection;

public class IsEmpty extends UnaryOperator<Collection<?>> {
  public IsEmpty(Collection<?> operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operand, stringWriter);
    stringWriter.appendString(" is empty");
  }
}
