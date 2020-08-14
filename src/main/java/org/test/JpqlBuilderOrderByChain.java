package org.test;

import org.test.query.SelectQuery;

public class JpqlBuilderOrderByChain<T> {
  private final JpqlStringBuilder<T> stringBuilder;
  private final SelectQuery query;

  JpqlBuilderOrderByChain(Object operand, JpqlStringBuilder<T> stringBuilder, SelectQuery query) {
    this.stringBuilder = stringBuilder;
    this.query = query;

    query.addOrderBy(operand);
  }

  public JpqlBuilderOrderByChain<T> orderBy(Object operand) {
    query.addOrderBy(operand);
    return this;
  }

  public JpqlBuilderOrderByChain<T> asc() {
    query.setOrderAsc();
    return this;
  }

  public JpqlBuilderOrderByChain<T> desc() {
    query.setOrderDesc();
    return this;
  }

  public JpqlBuilderOrderByChain<T> nullsFirst() {
    query.setOrderNullsFirst();
    return this;
  }

  public JpqlBuilderOrderByChain<T> nullsLast() {
    query.setOrderNullsLast();
    return this;
  }

  public String build() {
    return stringBuilder.build(query);
  }
}
