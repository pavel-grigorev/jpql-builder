package org.test.functions;

import org.test.querystring.JpqlStringWriter;

import java.util.Collection;

public class Size extends JpqlFunction<Integer> {
  private final Collection<?> argument;

  public Size(Collection<?> argument) {
    this.argument = argument;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("size(");
    writeOperand(argument, stringWriter);
    stringWriter.appendString(")");
  }
}
