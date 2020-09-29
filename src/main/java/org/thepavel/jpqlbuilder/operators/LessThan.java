package org.thepavel.jpqlbuilder.operators;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class LessThan<T> extends BinaryOperator<T, T> {
  public LessThan(T operandA, T operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);
    stringWriter.appendString(" < ");
    writeOperand(operandB, stringWriter);
  }
}
