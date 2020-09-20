package org.pavel.jpqlbuilder.functions;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.Date;

public class Extract extends JpqlFunction<Integer> {
  private final Object argument;
  private final Part part;

  public Extract(Date argument, Part part) {
    this.argument = argument;
    this.part = part;
  }

  public Extract(JpqlFunction<Date> argument, Part part) {
    this.argument = argument;
    this.part = part;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("extract(");
    stringWriter.appendString(part.name);
    stringWriter.appendString(" from ");
    writeOperand(argument, stringWriter);
    stringWriter.appendString(")");
  }

  public enum Part {
    YEAR("YEAR"),
    MONTH("MONTH"),
    DAY("DAY"),
    HOUR("HOUR"),
    MINUTE("MINUTE"),
    SECOND("SECOND");

    private final String name;

    Part(String name) {
      this.name = name;
    }
  }
}
