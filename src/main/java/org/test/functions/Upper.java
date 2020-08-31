package org.test.functions;

import org.test.querystring.JpqlStringWriter;

public class Upper extends JpqlFunction<String> {
  private final Object operand;

  Upper(String operand) {
    this.operand = operand;
  }

  Upper(JpqlFunction<String> nested) {
    this.operand = nested;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("upper(");
    writeOperand(operand, stringWriter);
    stringWriter.appendString(")");
  }
}
