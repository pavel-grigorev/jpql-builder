package org.test;

import org.test.operators.Operator;
import org.test.query.SelectQuery;
import org.test.operators.builders.BaseExpressionChain;

public class JpqlBuilderWhereChain<T> extends BaseExpressionChain<JpqlBuilderWhereChain<T>> {
  private final JpqlStringBuilder<T> stringBuilder;
  private final SelectQuery query;

  JpqlBuilderWhereChain(JpqlStringBuilder<T> stringBuilder, SelectQuery query) {
    this.stringBuilder = stringBuilder;
    this.query = query;
  }

  JpqlBuilderWhereChain(Operator operator, JpqlStringBuilder<T> stringBuilder, SelectQuery query) {
    super(operator);
    this.stringBuilder = stringBuilder;
    this.query = query;

    query.setWhere(operator);
  }

  @Override
  protected JpqlBuilderWhereChain<T> createChain(Operator operator) {
    return new JpqlBuilderWhereChain<>(operator, stringBuilder, query);
  }

  public JpqlBuilderOrderByChain<T> orderBy(Object operand) {
    return new JpqlBuilderOrderByChain<>(operand, stringBuilder, query);
  }

  public String build() {
    return stringBuilder.build(query);
  }
}
