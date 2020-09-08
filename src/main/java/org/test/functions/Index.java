package org.test.functions;

import org.test.querystring.JpqlStringWriter;
import org.test.utils.EntityHelper;

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
