package org.thepavel.jpqlbuilder.operators;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class And<A, B> extends BinaryOperator<A, B> {
  public And(A operandA, B operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);
    stringWriter.appendString(" and ");
    writeOperand(operandB, stringWriter);
  }
}
