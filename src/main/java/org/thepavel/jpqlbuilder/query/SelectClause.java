package org.thepavel.jpqlbuilder.query;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.thepavel.jpqlbuilder.operators.Operator;

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
