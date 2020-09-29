package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Length extends JpqlFunction<Integer> {
  private final Object string;

  public Length(String string) {
    this.string = string;
  }

  public Length(JpqlFunction<String> string) {
    this.string = string;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("length(");
    writeOperand(string, stringWriter);
    stringWriter.appendString(")");
  }
}
