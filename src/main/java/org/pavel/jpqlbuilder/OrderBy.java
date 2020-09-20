package org.pavel.jpqlbuilder;

import org.pavel.jpqlbuilder.query.SelectQuery;
import org.pavel.jpqlbuilder.querystring.JpqlStringBuilder;
import org.pavel.jpqlbuilder.path.PathResolver;

import java.util.Map;
import java.util.function.Function;

public class OrderBy<T> implements JpqlQuery {
  private final PathResolver<T> pathResolver;
  private final JpqlStringBuilder stringBuilder;
  private final SelectQuery query;

  OrderBy(Object operand, PathResolver<T> pathResolver, JpqlStringBuilder stringBuilder, SelectQuery query) {
    this.pathResolver = pathResolver;
    this.stringBuilder = stringBuilder;
    this.query = query;

    query.addOrderBy(operand);
  }

  public OrderBy<T> orderBy(Object operand) {
    query.addOrderBy(operand);
    return this;
  }

  public OrderBy<T> orderBy(Function<T, Object> operandFunction) {
    return orderBy(operandFunction.apply(getPathSpecifier()));
  }

  private T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }

  public OrderBy<T> asc() {
    query.setOrderAsc();
    return this;
  }

  public OrderBy<T> desc() {
    query.setOrderDesc();
    return this;
  }

  public OrderBy<T> nullsFirst() {
    query.setOrderNullsFirst();
    return this;
  }

  public OrderBy<T> nullsLast() {
    query.setOrderNullsLast();
    return this;
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
