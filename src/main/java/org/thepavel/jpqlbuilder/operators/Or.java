package org.thepavel.jpqlbuilder.operators;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Or<A, B> extends BinaryOperator<A, B> {
  public Or(A operandA, B operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);
    stringWriter.appendString(" or ");
    writeOperand(operandB, stringWriter);
  }
}
