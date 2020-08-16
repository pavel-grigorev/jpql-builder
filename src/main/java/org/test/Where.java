package org.test;

import org.test.operators.Operator;
import org.test.query.SelectQuery;
import org.test.operators.builders.BaseExpressionChain;

import java.util.Map;

public class Where<T> extends BaseExpressionChain<Where<T>> implements JpqlQuery {
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

  @Override
  public String getQueryString() {
    return stringBuilder.build(query);
  }

  @Override
  public Map<String, Object> getParameters() {
    return stringBuilder.getParameters();
  }
}
