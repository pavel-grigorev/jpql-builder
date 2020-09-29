package org.thepavel.jpqlbuilder.operators;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Not extends UnaryOperator<Operator> {
  public Not(Operator operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("not (");
    operand.writeTo(stringWriter);
    stringWriter.appendString(")");
  }
}
