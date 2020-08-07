package org.test;

import org.springframework.aop.support.AopUtils;
import org.test.operators.Parentheses;
import org.test.operators.builders.ExpressionChain;
import org.test.operators.builders.OperatorBuilder;
import org.test.operators.builders.StringOperatorBuilder;
import org.test.query.Join;
import org.test.utils.EntityHelper;

import java.util.Map;

public class JpqlBuilder<T> {
  private final AliasGenerator aliasGenerator = new AliasGenerator();
  private final PathResolverList joins = new PathResolverList();
  private final Join join = new Join();
  private final PathResolver<T> pathResolver;
  private final JpqlStringBuilder<T> builder;

  private JpqlBuilder(Class<T> entityClass) {
    requireEntityClass(entityClass);

    String rootAlias = aliasGenerator.next();

    pathResolver = new PathResolver<>(entityClass, rootAlias);
    builder = new JpqlStringBuilder<>(pathResolver, joins);
    builder.buildBaseQuery(entityClass, rootAlias);
  }

  private static void requireEntityClass(Class<?> entityClass) {
    if (!EntityHelper.isEntity(entityClass)) {
      throw new IllegalArgumentException("Class " + entityClass.getName() + " is not an entity class");
    }
  }

  public static <T> JpqlBuilder<T> select(Class<T> entityType) {
    return new JpqlBuilder<>(entityType);
  }

  @SuppressWarnings("unchecked")
  public <P> P join(P path) {
    Class<?> targetClass = AopUtils.getTargetClass(path);
    requireEntityClass(targetClass);

    String alias = aliasGenerator.next();
    join.add(alias, path);

    PathResolver<?> pathResolver = new PathResolver<>(targetClass, alias);
    joins.add(pathResolver);
    return (P) pathResolver.getPathSpecifier();
  }

  public <P> OperatorBuilder<P, JpqlBuilderWhereChain<T>> where(P operand) {
    writeJoins();
    return new OperatorBuilder<>(new JpqlBuilderWhereChain<>(builder), operand);
  }

  public StringOperatorBuilder<JpqlBuilderWhereChain<T>> where(String operand) {
    writeJoins();
    return new StringOperatorBuilder<>(new JpqlBuilderWhereChain<>(builder), operand);
  }

  public JpqlBuilderWhereChain<T> where(ExpressionChain chain) {
    writeJoins();
    return new JpqlBuilderWhereChain<>(new Parentheses(chain.getOperator()), builder);
  }

  public JpqlBuilderOrderByChain<T> orderBy(Object operand) {
    writeJoins();
    return new JpqlBuilderOrderByChain<>(builder, operand);
  }

  public String build() {
    writeJoins();
    return builder.build();
  }

  private void writeJoins() {
    join.writeTo(builder);
  }

  public Map<String, Object> getParameters() {
    return builder.getParameters();
  }

  public T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }
}
