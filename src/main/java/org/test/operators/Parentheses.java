package org.test.operators;

import org.test.querystring.JpqlStringWriter;

public class Parentheses extends UnaryOperator<Operator> {
  public Parentheses(Operator operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("(");
    writeOperand(operand, stringWriter);
    stringWriter.appendString(")");
  }
}
