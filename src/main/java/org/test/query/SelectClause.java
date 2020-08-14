package org.test.query;

import org.test.JpqlStringBuilder;
import org.test.operators.Operator;

public class SelectClause extends Operator {
  private final String alias;
  private final Class<?> entityClass;

  public SelectClause(String alias, Class<?> entityClass) {
    this.alias = alias;
    this.entityClass = entityClass;
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    stringBuilder.appendString("select ");
    stringBuilder.appendString(alias);
    stringBuilder.appendString(" from ");
    stringBuilder.appendValue(entityClass);
    stringBuilder.appendString(" ");
    stringBuilder.appendString(alias);
  }
}
