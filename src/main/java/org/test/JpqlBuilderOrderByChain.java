package org.test;

import org.test.query.OrderBy;

public class JpqlBuilderOrderByChain<T> {
  private final JpqlStringBuilder<T> stringBuilder;
  private final OrderBy orderBy;

  JpqlBuilderOrderByChain(JpqlStringBuilder<T> stringBuilder, Object operand) {
    this.stringBuilder = stringBuilder;
    orderBy = new OrderBy(operand);
  }

  public JpqlBuilderOrderByChain<T> orderBy(Object operand) {
    orderBy.addItem(operand);
    return this;
  }

  public JpqlBuilderOrderByChain<T> asc() {
    orderBy.setAsc();
    return this;
  }

  public JpqlBuilderOrderByChain<T> desc() {
    orderBy.setDesc();
    return this;
  }

  public JpqlBuilderOrderByChain<T> nullsFirst() {
    orderBy.setNullsFirst();
    return this;
  }

  public JpqlBuilderOrderByChain<T> nullsLast() {
    orderBy.setNullsLast();
    return this;
  }

  public String build() {
    orderBy.writeTo(stringBuilder);
    return stringBuilder.build();
  }
}
