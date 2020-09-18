package org.test.functions;

import org.test.querystring.JpqlStringWriter;
import org.test.utils.EntityHelper;

public class Column<T> extends JpqlFunction<T> {
  private final String name;
  private final Object entity;

  public Column(String name, Object entity) {
    if (name == null || entity == null) {
      throw new NullPointerException();
    }
    if (name.indexOf('\'') != -1) {
      throw new IllegalArgumentException("single quote character is not allowed");
    }
    if (!EntityHelper.isProxiedEntity(entity)) {
      throw new IllegalArgumentException("not an entity");
    }
    this.name = name;
    this.entity = entity;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("column('");
    stringWriter.appendString(name);
    stringWriter.appendString("', ");
    writeOperand(entity, stringWriter);
    stringWriter.appendString(")");
  }
}