package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.thepavel.jpqlbuilder.utils.EntityHelper;

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
