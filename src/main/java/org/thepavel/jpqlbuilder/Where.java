package org.thepavel.jpqlbuilder;

import org.thepavel.jpqlbuilder.operators.builders.BaseExpressionChain;
import org.thepavel.jpqlbuilder.querystring.JpqlStringBuilder;
import org.thepavel.jpqlbuilder.operators.Operator;
import org.thepavel.jpqlbuilder.path.PathResolver;
import org.thepavel.jpqlbuilder.query.SelectQuery;

import java.util.Map;
import java.util.function.Function;

public class Where<T> extends BaseExpressionChain<Where<T>> implements JpqlQuery {
  private final PathResolver<T> pathResolver;
  private final JpqlStringBuilder stringBuilder;
  private final SelectQuery query;

  Where(PathResolver<T> pathResolver, JpqlStringBuilder stringBuilder, SelectQuery query) {
    this.pathResolver = pathResolver;
    this.stringBuilder = stringBuilder;
    this.query = query;
  }

  Where(Operator operator, PathResolver<T> pathResolver, JpqlStringBuilder stringBuilder, SelectQuery query) {
    super(operator);
    this.pathResolver = pathResolver;
    this.stringBuilder = stringBuilder;
    this.query = query;

    query.setWhere(operator);
  }

  @Override
  protected void onOperatorChange(Operator operator) {
    query.setWhere(operator);
  }

  public OrderBy<T> orderBy(Object operand) {
    return new OrderBy<>(operand, pathResolver, stringBuilder, query);
  }

  public OrderBy<T> orderBy(Function<T, Object> operandFunction) {
    return orderBy(operandFunction.apply(getPathSpecifier()));
  }

  private T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
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
