package org.pavel.jpqlbuilder.functions;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.Date;

public class CurrentTimestamp extends JpqlFunction<Date> {
  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("current_timestamp");
  }
}
