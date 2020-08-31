package org.test.functions;

import org.test.querystring.JpqlStringWriter;

public class Upper extends JpqlFunction<String> {
  private final Object parameter;

  Upper(String parameter) {
    this.parameter = parameter;
  }

  Upper(JpqlFunction<String> nested) {
    this.parameter = nested;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("upper(");
    writeOperand(parameter, stringWriter);
    stringWriter.appendString(")");
  }
}
