package org.test;

import org.test.operators.Parentheses;
import org.test.operators.builders.ExpressionChain;
import org.test.operators.builders.OperatorBuilder;
import org.test.operators.builders.StringOperatorBuilder;
import org.test.utils.EntityHelper;

import java.util.HashMap;
import java.util.Map;

public class JpqlBuilder<T> {
  private static final String ROOT_ALIAS = "e";

  private final StringBuilder builder = new StringBuilder();
  private final Map<String, Object> parameters = new HashMap<>();
  private final PathResolver<T> pathResolver;
  // TODO: build alias factory to support more aliases
  private char currentParameterAlias = 'a';

  private JpqlBuilder(Class<T> entityClass) {
    if (!EntityHelper.isEntity(entityClass)) {
      throw new IllegalArgumentException("Class " + entityClass.getName() + " is not an entity class");
    }
    pathResolver = new PathResolver<>(entityClass, ROOT_ALIAS);
    builder
        .append("select ")
        .append(ROOT_ALIAS)
        .append(" from ")
        .append(EntityHelper.getEntityName(entityClass))
        .append(' ')
        .append(ROOT_ALIAS);
  }

  // TODO: hide this api
  public void appendString(String s) {
    builder.append(s);
  }

  // TODO: hide this api
  public void appendValue(Object value) {
    builder.append(resolvePathOrParameter(value));
  }

  private String resolvePathOrParameter(Object value) {
    String path = pathResolver.getPropertyPath(value);
    return path != null ? path : getParameter(value);
  }

  private String getParameter(Object value) {
    String alias = getNextParameterAlias();
    parameters.put(alias, value);
    return ':' + alias;
  }

  private String getNextParameterAlias() {
    return String.valueOf(currentParameterAlias++);
  }

  public static <T> JpqlBuilder<T> select(Class<T> entityType) {
    return new JpqlBuilder<>(entityType);
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

  public T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }

  public <P> OperatorBuilder<P, JpqlBuilderWhereChain<T>> where(P operand) {
    return new OperatorBuilder<>(new JpqlBuilderWhereChain<>(this), operand);
  }

  public StringOperatorBuilder<JpqlBuilderWhereChain<T>> where(String operand) {
    return new StringOperatorBuilder<>(new JpqlBuilderWhereChain<>(this), operand);
  }

  public JpqlBuilderWhereChain<T> where(ExpressionChain chain) {
    return new JpqlBuilderWhereChain<>(new Parentheses(chain.getOperator()), this);
  }

  public String build() {
    return builder.toString();
  }
}
