package org.test;

import org.test.operators.Operator;
import org.test.operators.builders.BaseExpressionChain;

public class JpqlBuilderWhereChain<T> extends BaseExpressionChain<JpqlBuilderWhereChain<T>> {
  private final JpqlBuilder<T> jpqlBuilder;

  JpqlBuilderWhereChain(JpqlBuilder<T> jpqlBuilder) {
    super();
    this.jpqlBuilder = jpqlBuilder;
  }

  JpqlBuilderWhereChain(Operator operator, JpqlBuilder<T> jpqlBuilder) {
    super(operator);
    this.jpqlBuilder = jpqlBuilder;
  }

  @Override
  protected JpqlBuilderWhereChain<T> createChain(Operator operator) {
    return new JpqlBuilderWhereChain<>(operator, jpqlBuilder);
  }

  public String build() {
    jpqlBuilder.appendString(" where ");
    getOperator().writeTo(jpqlBuilder);
    return jpqlBuilder.build();
  }
}
