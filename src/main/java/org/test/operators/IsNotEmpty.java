package org.test.operators;

import org.test.querystring.JpqlStringWriter;

import java.util.Collection;

public class IsNotEmpty extends UnaryOperator<Collection<?>> {
  public IsNotEmpty(Collection<?> operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operand, stringWriter);
    stringWriter.appendString(" is not empty");
  }
}