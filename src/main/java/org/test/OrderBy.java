package org.test;

import org.test.query.SelectQuery;

public class OrderBy<T> {
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

  public String build() {
    return stringBuilder.build(query);
  }
}
