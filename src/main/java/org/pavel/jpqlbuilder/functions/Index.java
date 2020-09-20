package org.pavel.jpqlbuilder.functions;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.pavel.jpqlbuilder.utils.EntityHelper;

public class Index extends JpqlFunction<Integer> {
  private final Object argument;

  public Index(Object argument) {
    if (!EntityHelper.isProxiedEntity(argument)) {
      throw new IllegalArgumentException("argument is not a joined entity");
    }
    this.argument = argument;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("index(");
    writeOperand(argument, stringWriter);
    stringWriter.appendString(")");
  }
}
