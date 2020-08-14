package org.test;

import org.test.operators.Operator;
import org.test.query.SelectQuery;
import org.test.operators.builders.BaseExpressionChain;

public class Where<T> extends BaseExpressionChain<Where<T>> {
  private final JpqlStringBuilder<T> stringBuilder;
  private final SelectQuery query;

  Where(JpqlStringBuilder<T> stringBuilder, SelectQuery query) {
    this.stringBuilder = stringBuilder;
    this.query = query;
  }

  Where(Operator operator, JpqlStringBuilder<T> stringBuilder, SelectQuery query) {
    super(operator);
    this.stringBuilder = stringBuilder;
    this.query = query;

    query.setWhere(operator);
  }

  @Override
  protected void onOperatorChange(Operator operator) {
    query.setWhere(operator);
  }

  public OrderBy<T> orderBy(Object operand) {
    return new OrderBy<>(operand, stringBuilder, query);
  }

  public String build() {
    return stringBuilder.build(query);
  }
}
