package org.pavel.jpqlbuilder.functions;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

public class RegExp extends JpqlFunction<Boolean> {
  private final Object argument;
  private final String regExp;

  public RegExp(String argument, String regExp) {
    this.argument = argument;
    this.regExp = regExp;
  }

  public RegExp(JpqlFunction<String> argument, String regExp) {
    this.argument = argument;
    this.regExp = regExp;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(argument, stringWriter);
    stringWriter.appendString(" regexp ");
    writeOperand(regExp, stringWriter);
  }
}
