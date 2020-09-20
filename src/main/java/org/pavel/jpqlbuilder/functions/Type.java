package org.pavel.jpqlbuilder.functions;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.pavel.jpqlbuilder.utils.EntityHelper;

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
