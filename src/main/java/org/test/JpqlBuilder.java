package org.test;

import org.test.operators.Parentheses;
import org.test.operators.builders.ExpressionChain;
import org.test.operators.builders.OperatorBuilder;
import org.test.operators.builders.StringOperatorBuilder;
import org.test.utils.EntityHelper;

import java.util.Map;

public class JpqlBuilder<T> {
  private final AliasGenerator aliasGenerator = new AliasGenerator();
  private final PathResolver<T> pathResolver;
  private final JpqlStringBuilder<T> builder;

  private JpqlBuilder(Class<T> entityClass) {
    if (!EntityHelper.isEntity(entityClass)) {
      throw new IllegalArgumentException("Class " + entityClass.getName() + " is not an entity class");
    }

    String rootAlias = aliasGenerator.next();

    pathResolver = new PathResolver<>(entityClass, rootAlias);
    builder = new JpqlStringBuilder<>(pathResolver);
    builder.buildBaseQuery(entityClass, rootAlias);
  }

  public static <T> JpqlBuilder<T> select(Class<T> entityType) {
    return new JpqlBuilder<>(entityType);
  }

  public String build() {
    return builder.build();
  }

  public Map<String, Object> getParameters() {
    return builder.getParameters();
  }

  public T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }

  public <P> OperatorBuilder<P, JpqlBuilderWhereChain<T>> where(P operand) {
    return new OperatorBuilder<>(new JpqlBuilderWhereChain<>(builder), operand);
  }

  public StringOperatorBuilder<JpqlBuilderWhereChain<T>> where(String operand) {
    return new StringOperatorBuilder<>(new JpqlBuilderWhereChain<>(builder), operand);
  }

  public JpqlBuilderWhereChain<T> where(ExpressionChain chain) {
    return new JpqlBuilderWhereChain<>(new Parentheses(chain.getOperator()), builder);
  }

  public JpqlBuilderOrderByChain<T> orderBy(Object operand) {
    return new JpqlBuilderOrderByChain<>(builder, operand);
  }
}
