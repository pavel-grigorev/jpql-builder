package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.Date;

public class CurrentDate extends JpqlFunction<Date> {
  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("current_date");
  }
}
