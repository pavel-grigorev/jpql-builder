package org.test;

import org.springframework.aop.support.AopUtils;
import org.test.operators.Operator;
import org.test.operators.Parentheses;
import org.test.operators.UnaryOperator;
import org.test.operators.builders.ExpressionChain;
import org.test.operators.builders.OperatorBuilder;
import org.test.operators.builders.StringOperatorBuilder;
import org.test.query.JoinClause;
import org.test.query.JoinType;
import org.test.query.SelectQuery;
import org.test.utils.EntityHelper;

import java.util.Collection;
import java.util.Map;

public class JpqlBuilder<T> {
  private final AliasGenerator aliasGenerator = new AliasGenerator();
  private final PathResolverList joinedPathResolvers = new PathResolverList();
  private final PathResolver<T> pathResolver;
  private final JpqlStringBuilder<T> stringBuilder;
  private final SelectQuery query;

  private JpqlBuilder(Class<T> entityClass) {
    requireEntityClass(entityClass);

    String rootAlias = aliasGenerator.next();

    pathResolver = new PathResolver<>(entityClass, rootAlias);
    stringBuilder = new JpqlStringBuilder<>(pathResolver, joinedPathResolvers);
    query = new SelectQuery(rootAlias, entityClass);
  }

  private static void requireEntityClass(Class<?> entityClass) {
    if (!EntityHelper.isEntity(entityClass)) {
      throw new IllegalArgumentException("Class " + entityClass.getName() + " is not an entity class");
    }
  }

  public static <T> JpqlBuilder<T> select(Class<T> entityType) {
    return new JpqlBuilder<>(entityType);
  }

  public <P> JpqlBuilderJoinChain<P> join(P path) {
    return joinAssociation(path, JoinType.INNER);
  }

  public <P> JpqlBuilderJoinChain<P> join(Collection<P> path) {
    return joinCollection(path, JoinType.INNER);
  }

  public <P> JpqlBuilderJoinChain<P> join(Class<P> entityClass) {
    return joinClass(entityClass, JoinType.INNER);
  }

  public <P> JpqlBuilderJoinChain<P> leftJoin(P path) {
    return joinAssociation(path, JoinType.LEFT);
  }

  public <P> JpqlBuilderJoinChain<P> leftJoin(Collection<P> path) {
    return joinCollection(path, JoinType.LEFT);
  }

  public <P> JpqlBuilderJoinChain<P> leftJoin(Class<P> entityClass) {
    return joinClass(entityClass, JoinType.LEFT);
  }

  public void joinFetch(Object path) {
    joinAssociation(path, JoinType.FETCH);
  }

  public void joinFetch(Collection<?> path) {
    joinCollection(path, JoinType.FETCH);
  }

  public <P> JpqlBuilderJoinChain<P> joinFetchWithAlias(P path) {
    return joinAssociation(path, JoinType.FETCH_WITH_ALIAS);
  }

  public <P> JpqlBuilderJoinChain<P> joinFetchWithAlias(Collection<P> path) {
    return joinCollection(path, JoinType.FETCH_WITH_ALIAS);
  }

  private <P> JpqlBuilderJoinChain<P> joinAssociation(P path, JoinType type) {
    return join(path, AopUtils.getTargetClass(path), type);
  }

  private <P> JpqlBuilderJoinChain<P> joinCollection(Collection<P> path, JoinType type) {
    P item = path.iterator().next();
    return join(path, item.getClass(), type);
  }

  private <P> JpqlBuilderJoinChain<P> joinClass(Class<P> entityClass, JoinType type) {
    return join(entityClass, entityClass, type);
  }

  // TODO: refactor into JpqlBuilderJoinChainFactory
  @SuppressWarnings("unchecked")
  private <P> JpqlBuilderJoinChain<P> join(Object joinedThing, Class<?> targetClass, JoinType type) {
    requireEntityClass(targetClass);

    Class<P> castedClass = (Class<P>) targetClass;
    String alias = getNextAlias(type);

    PathResolver<P> pathResolver = new PathResolver<>(castedClass, alias);
    joinedPathResolvers.add(pathResolver);

    JoinClause joinClause = new JoinClause(alias, joinedThing, type);
    query.addJoin(joinClause);

    return new JpqlBuilderJoinChain<>(joinClause, pathResolver);
  }

  private String getNextAlias(JoinType type) {
    return type.hasAlias() ? aliasGenerator.next() : "";
  }

  public <P> OperatorBuilder<P, JpqlBuilderWhereChain<T>> where(P operand) {
    return new OperatorBuilder<>(createWhere(), operand);
  }

  public StringOperatorBuilder<JpqlBuilderWhereChain<T>> where(String operand) {
    return new StringOperatorBuilder<>(createWhere(), operand);
  }

  public StringOperatorBuilder<JpqlBuilderWhereChain<T>> where(UnaryOperator<String> operator) {
    return new StringOperatorBuilder<>(createWhere(), operator);
  }

  public JpqlBuilderWhereChain<T> where(ExpressionChain chain) {
    return createWhere(new Parentheses(chain.getOperator()));
  }

  private JpqlBuilderWhereChain<T> createWhere() {
    return new JpqlBuilderWhereChain<>(stringBuilder, query);
  }

  private JpqlBuilderWhereChain<T> createWhere(Operator operator) {
    return new JpqlBuilderWhereChain<>(operator, stringBuilder, query);
  }

  public JpqlBuilderOrderByChain<T> orderBy(Object operand) {
    return new JpqlBuilderOrderByChain<>(operand, stringBuilder, query);
  }

  public String build() {
    return stringBuilder.build(query);
  }

  public Map<String, Object> getParameters() {
    return stringBuilder.getParameters();
  }

  public T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }
}
