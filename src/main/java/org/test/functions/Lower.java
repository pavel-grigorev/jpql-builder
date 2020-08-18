package org.test.functions;

import org.test.querystring.JpqlStringWriter;
import org.test.operators.UnaryOperator;

public class Lower extends UnaryOperator<String> {
  Lower(String operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("lower(");
    writeOperand(operand, stringWriter);
    stringWriter.appendString(")");
  }
}
