package org.test.functions;

import org.test.querystring.JpqlStringWriter;
import org.test.operators.UnaryOperator;

public class Upper extends UnaryOperator<String> {
  Upper(String operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("upper(");
    writeOperand(operand, stringWriter);
    stringWriter.appendString(")");
  }
}
