package org.pavel.jpqlbuilder.functions;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Trim extends JpqlFunction<String> {
  private static final char DEFAULT_TRIM_CHAR = ' ';

  private final Object parameter;
  private final char trimChar;

  Trim(String parameter) {
    this(parameter, DEFAULT_TRIM_CHAR);
  }

  Trim(String parameter, char trimChar) {
    this.parameter = parameter;
    this.trimChar = trimChar;
  }

  Trim(JpqlFunction<String> nested) {
    this(nested, DEFAULT_TRIM_CHAR);
  }

  Trim(JpqlFunction<String> nested, char trimChar) {
    this.parameter = nested;
    this.trimChar = trimChar;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("trim(");
    appendTrimChar(stringWriter);
    writeOperand(parameter, stringWriter);
    stringWriter.appendString(")");
  }

  private void appendTrimChar(JpqlStringWriter stringWriter) {
    if (trimChar != DEFAULT_TRIM_CHAR) {
      writeOperand(trimChar, stringWriter);
      stringWriter.appendString(" from ");
    }
  }
}
