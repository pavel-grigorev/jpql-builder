package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.thepavel.jpqlbuilder.utils.EntityHelper;

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
