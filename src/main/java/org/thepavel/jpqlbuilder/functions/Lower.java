package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Lower extends JpqlFunction<String> {
  private final Object parameter;

  Lower(String parameter) {
    this.parameter = parameter;
  }

  Lower(JpqlFunction<String> nested) {
    this.parameter = nested;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("lower(");
    writeOperand(parameter, stringWriter);
    stringWriter.appendString(")");
  }
}
