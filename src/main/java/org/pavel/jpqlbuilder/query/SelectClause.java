package org.pavel.jpqlbuilder.query;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.pavel.jpqlbuilder.operators.Operator;

public class SelectClause implements Operator {
  private final String alias;
  private final Class<?> entityClass;

  public SelectClause(String alias, Class<?> entityClass) {
    this.alias = alias;
    this.entityClass = entityClass;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("select ");
    stringWriter.appendString(alias);
    stringWriter.appendString(" from ");
    stringWriter.appendValue(entityClass);
    stringWriter.appendString(" ");
    stringWriter.appendString(alias);
  }
}
