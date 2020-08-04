package org.test;

import org.test.operators.Operator;
import org.test.operators.Where;
import org.test.operators.builders.BaseExpressionChain;

public class JpqlBuilderWhereChain<T> extends BaseExpressionChain<JpqlBuilderWhereChain<T>> {
  private final JpqlStringBuilder<T> stringBuilder;

  JpqlBuilderWhereChain(JpqlStringBuilder<T> stringBuilder) {
    super();
    this.stringBuilder = stringBuilder;
  }

  JpqlBuilderWhereChain(Operator operator, JpqlStringBuilder<T> stringBuilder) {
    super(operator);
    this.stringBuilder = stringBuilder;
  }

  @Override
  protected JpqlBuilderWhereChain<T> createChain(Operator operator) {
    return new JpqlBuilderWhereChain<>(operator, stringBuilder);
  }

  public JpqlBuilderOrderByChain<T> orderBy(Object... values) {
    writeWhereClause();
    return new JpqlBuilderOrderByChain<>(stringBuilder, values);
  }

  public String build() {
    writeWhereClause();
    return stringBuilder.build();
  }

  private void writeWhereClause() {
    new Where(getOperator()).writeTo(stringBuilder);
  }
}
