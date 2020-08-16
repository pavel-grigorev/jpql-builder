package org.test;

import org.test.query.SelectQuery;

import java.util.Map;

public class OrderBy<T> implements JpqlQuery {
  private final JpqlStringBuilder<T> stringBuilder;
  private final SelectQuery query;

  OrderBy(Object operand, JpqlStringBuilder<T> stringBuilder, SelectQuery query) {
    this.stringBuilder = stringBuilder;
    this.query = query;

    query.addOrderBy(operand);
  }

  public OrderBy<T> orderBy(Object operand) {
    query.addOrderBy(operand);
    return this;
  }

  public OrderBy<T> asc() {
    query.setOrderAsc();
    return this;
  }

  public OrderBy<T> desc() {
    query.setOrderDesc();
    return this;
  }

  public OrderBy<T> nullsFirst() {
    query.setOrderNullsFirst();
    return this;
  }

  public OrderBy<T> nullsLast() {
    query.setOrderNullsLast();
    return this;
  }

  @Override
  public String getQueryString() {
    return stringBuilder.build(query);
  }

  @Override
  public Map<String, Object> getParameters() {
    return stringBuilder.getParameters();
  }
}
