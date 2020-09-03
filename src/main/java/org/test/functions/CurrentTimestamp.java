package org.test.functions;

import org.test.querystring.JpqlStringWriter;

import java.util.Date;

public class CurrentTimestamp extends JpqlFunction<Date> {
  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("current_timestamp");
  }
}
