package org.test;

import org.test.operators.Operator;
import org.test.query.Where;
import org.test.operators.builders.BaseExpressionChain;

import java.util.function.Function;

public class JpqlBuilderWhereChain<T> extends BaseExpressionChain<JpqlBuilderWhereChain<T>> {
  private final PathResolver<T> pathResolver;
  private final JpqlStringBuilder<T> stringBuilder;

  JpqlBuilderWhereChain(PathResolver<T> pathResolver, JpqlStringBuilder<T> stringBuilder) {
    this.pathResolver = pathResolver;
    this.stringBuilder = stringBuilder;
  }

  JpqlBuilderWhereChain(Operator operator, PathResolver<T> pathResolver, JpqlStringBuilder<T> stringBuilder) {
    super(operator);
    this.pathResolver = pathResolver;
    this.stringBuilder = stringBuilder;
  }

  @Override
  protected JpqlBuilderWhereChain<T> createChain(Operator operator) {
    return new JpqlBuilderWhereChain<>(operator, pathResolver, stringBuilder);
  }

  public JpqlBuilderOrderByChain<T> orderBy(Object operand) {
    writeWhereClause();
    return createOrderBy(operand);
  }

  public JpqlBuilderOrderByChain<T> orderBy(Function<T, Object> operandFunction) {
    writeWhereClause();
    return createOrderBy(operandFunction.apply(getPathSpecifier()));
  }

  public String build() {
    writeWhereClause();
    return stringBuilder.build();
  }

  private void writeWhereClause() {
    new Where(getOperator()).writeTo(stringBuilder);
  }

  private JpqlBuilderOrderByChain<T> createOrderBy(Object operand) {
    return new JpqlBuilderOrderByChain<>(pathResolver, stringBuilder, operand);
  }

  private T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }
}
