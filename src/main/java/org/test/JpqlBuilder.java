package org.test;

import org.springframework.aop.support.AopUtils;
import org.test.operators.Parentheses;
import org.test.operators.builders.ExpressionChain;
import org.test.operators.builders.OperatorBuilder;
import org.test.operators.builders.StringOperatorBuilder;
import org.test.query.Join;
import org.test.query.JoinType;
import org.test.utils.EntityHelper;

import java.util.Collection;
import java.util.Map;

public class JpqlBuilder<T> {
  private final AliasGenerator aliasGenerator = new AliasGenerator();
  private final PathResolverList joins = new PathResolverList();
  private final Join joinClause = new Join();
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

  public <P> P join(P path) {
    return join(path, AopUtils.getTargetClass(path), JoinType.INNER);
  }

  public <P> P join(Collection<P> path) {
    P item = path.iterator().next();
    return join(path, item.getClass(), JoinType.INNER);
  }

  public <P> P leftJoin(P path) {
    return join(path, AopUtils.getTargetClass(path), JoinType.LEFT);
  }

  public <P> P leftJoin(Collection<P> path) {
    P item = path.iterator().next();
    return join(path, item.getClass(), JoinType.LEFT);
  }

  public void joinFetch(Object path) {
    join(path, AopUtils.getTargetClass(path), JoinType.FETCH);
  }

  public void joinFetch(Collection<?> path) {
    Object item = path.iterator().next();
    join(path, item.getClass(), JoinType.FETCH);
  }

  @SuppressWarnings("unchecked")
  private <P> P join(Object path, Class<?> targetClass, JoinType type) {
    requireEntityClass(targetClass);

    String alias = getNextAlias(type);
    joinClause.add(alias, path, type);

    PathResolver<?> pathResolver = new PathResolver<>(targetClass, alias);
    joins.add(pathResolver);
    return (P) pathResolver.getPathSpecifier();
  }

  private String getNextAlias(JoinType type) {
    return type == JoinType.FETCH ? "" : aliasGenerator.next();
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
    joinClause.writeTo(builder);
  }

  public Map<String, Object> getParameters() {
    return builder.getParameters();
  }

  public T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }
}
