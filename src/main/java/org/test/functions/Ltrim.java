package org.test.functions;

import org.test.querystring.JpqlStringWriter;

public class Ltrim extends JpqlFunction<String> {
  private static final char DEFAULT_TRIM_CHAR = ' ';

  private final Object parameter;
  private final char trimChar;

  public Ltrim(String parameter) {
    this(parameter, DEFAULT_TRIM_CHAR);
  }

  public Ltrim(String parameter, char trimChar) {
    this.parameter = parameter;
    this.trimChar = trimChar;
  }

  public Ltrim(JpqlFunction<String> nested) {
    this(nested, DEFAULT_TRIM_CHAR);
  }

  public Ltrim(JpqlFunction<String> nested, char trimChar) {
    this.parameter = nested;
    this.trimChar = trimChar;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("trim(");
    stringWriter.appendString(getTrimTypeKeyword());
    appendTrimChar(stringWriter);
    stringWriter.appendString("from ");
    writeOperand(parameter, stringWriter);
    stringWriter.appendString(")");
  }

  String getTrimTypeKeyword() {
    return "leading ";
  }

  private void appendTrimChar(JpqlStringWriter stringWriter) {
    if (trimChar != DEFAULT_TRIM_CHAR) {
      writeOperand(trimChar, stringWriter);
      stringWriter.appendString(" ");
    }
  }
}
