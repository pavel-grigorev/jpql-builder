package org.test.functions;

import org.test.querystring.JpqlStringWriter;
import org.test.utils.EntityHelper;

public class Type extends JpqlFunction<Class<?>> {
  private final Object argument;

  public Type(Object argument) {
    if (!EntityHelper.isProxiedEntity(argument)) {
      throw new IllegalArgumentException("argument is not an entity");
    }
    this.argument = argument;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("type(");
    writeOperand(argument, stringWriter);
    stringWriter.appendString(")");
  }
}
