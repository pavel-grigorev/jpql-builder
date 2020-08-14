package org.test;

import org.springframework.aop.support.AopUtils;
import org.test.operators.Operator;
import org.test.operators.Parentheses;
import org.test.operators.UnaryOperator;
import org.test.operators.builders.ExpressionChain;
import org.test.operators.builders.OperatorBuilder;
import org.test.operators.builders.StringOperatorBuilder;
import org.test.path.PathResolver;
import org.test.path.PathResolverList;
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

  public <P> Join<P> join(P path) {
    return joinAssociation(path, JoinType.INNER);
  }

  public <P> Join<P> join(Collection<P> path) {
    return joinCollection(path, JoinType.INNER);
  }

  public <P> Join<P> join(Class<P> entityClass) {
    return joinClass(entityClass, JoinType.INNER);
  }

  public <P> Join<P> leftJoin(P path) {
    return joinAssociation(path, JoinType.LEFT);
  }

  public <P> Join<P> leftJoin(Collection<P> path) {
    return joinCollection(path, JoinType.LEFT);
  }

  public <P> Join<P> leftJoin(Class<P> entityClass) {
    return joinClass(entityClass, JoinType.LEFT);
  }

  public void joinFetch(Object path) {
    joinAssociation(path, JoinType.FETCH);
  }

  public void joinFetch(Collection<?> path) {
    joinCollection(path, JoinType.FETCH);
  }

  public <P> Join<P> joinFetchWithAlias(P path) {
    return joinAssociation(path, JoinType.FETCH_WITH_ALIAS);
  }

  public <P> Join<P> joinFetchWithAlias(Collection<P> path) {
    return joinCollection(path, JoinType.FETCH_WITH_ALIAS);
  }

  private <P> Join<P> joinAssociation(P path, JoinType type) {
    return join(path, AopUtils.getTargetClass(path), type);
  }

  private <P> Join<P> joinCollection(Collection<P> path, JoinType type) {
    P item = path.iterator().next();
    return join(path, item.getClass(), type);
  }

  private <P> Join<P> joinClass(Class<P> entityClass, JoinType type) {
    return join(entityClass, entityClass, type);
  }

  // TODO: refactor into JpqlBuilderJoinChainFactory
  @SuppressWarnings("unchecked")
  private <P> Join<P> join(Object joinedThing, Class<?> targetClass, JoinType type) {
    requireEntityClass(targetClass);

    Class<P> castedClass = (Class<P>) targetClass;
    String alias = getNextAlias(type);

    PathResolver<P> pathResolver = new PathResolver<>(castedClass, alias);
    joinedPathResolvers.add(pathResolver);

    JoinClause joinClause = new JoinClause(alias, joinedThing, type);
    query.addJoin(joinClause);

    return new Join<>(joinClause, pathResolver);
  }

  private String getNextAlias(JoinType type) {
    return type.hasAlias() ? aliasGenerator.next() : "";
  }

  public <P> OperatorBuilder<P, Where<T>> where(P operand) {
    return new OperatorBuilder<>(createWhere(), operand);
  }

  public StringOperatorBuilder<Where<T>> where(String operand) {
    return new StringOperatorBuilder<>(createWhere(), operand);
  }

  public StringOperatorBuilder<Where<T>> where(UnaryOperator<String> operator) {
    return new StringOperatorBuilder<>(createWhere(), operator);
  }

  public Where<T> where(ExpressionChain chain) {
    return createWhere(new Parentheses(chain.getOperator()));
  }

  private Where<T> createWhere() {
    return new Where<>(stringBuilder, query);
  }

  private Where<T> createWhere(Operator operator) {
    return new Where<>(operator, stringBuilder, query);
  }

  public OrderBy<T> orderBy(Object operand) {
    return new OrderBy<>(operand, stringBuilder, query);
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
