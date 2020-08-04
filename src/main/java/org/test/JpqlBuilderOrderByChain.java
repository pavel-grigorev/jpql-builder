package org.test;

import org.test.operators.OrderBy;

import java.util.Arrays;

public class JpqlBuilderOrderByChain<T> {
  private final JpqlStringBuilder<T> stringBuilder;
  private final OrderBy<Object> orderBy;

  JpqlBuilderOrderByChain(JpqlStringBuilder<T> stringBuilder, Object... values) {
    this.stringBuilder = stringBuilder;
    orderBy = new OrderBy<>(Arrays.asList(values));
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
