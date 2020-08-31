package org.test.functions;

import org.test.querystring.JpqlStringWriter;

public class Lower extends JpqlFunction<String> {
  private final Object operand;

  Lower(String operand) {
    this.operand = operand;
  }

  Lower(JpqlFunction<String> nested) {
    this.operand = nested;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("lower(");
    writeOperand(operand, stringWriter);
    stringWriter.appendString(")");
  }
}
