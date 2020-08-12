package org.test;

import org.springframework.aop.support.AopUtils;
import org.test.operators.Parentheses;
import org.test.operators.UnaryOperator;
import org.test.operators.builders.ExpressionChain;
import org.test.operators.builders.OperatorBuilder;
import org.test.operators.builders.StringOperatorBuilder;
import org.test.query.JoinType;
import org.test.utils.EntityHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JpqlBuilder<T> {
  private final AliasGenerator aliasGenerator = new AliasGenerator();
  private final PathResolverList joinedPathResolvers = new PathResolverList();
  private final List<JpqlBuilderJoinChain<?>> joins = new ArrayList<>();
  private final PathResolver<T> pathResolver;
  private final JpqlStringBuilder<T> builder;

  private JpqlBuilder(Class<T> entityClass) {
    requireEntityClass(entityClass);

    String rootAlias = aliasGenerator.next();

    pathResolver = new PathResolver<>(entityClass, rootAlias);
    builder = new JpqlStringBuilder<>(pathResolver, joinedPathResolvers);
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

    JpqlBuilderJoinChain<P> joinChain = new JpqlBuilderJoinChain<>(alias, joinedThing, type, pathResolver);
    joins.add(joinChain);
    return joinChain;
  }

  private String getNextAlias(JoinType type) {
    return type.hasAlias() ? aliasGenerator.next() : "";
  }

  public <P> OperatorBuilder<P, JpqlBuilderWhereChain<T>> where(P operand) {
    writeJoins();
    return new OperatorBuilder<>(new JpqlBuilderWhereChain<>(builder), operand);
  }

  public StringOperatorBuilder<JpqlBuilderWhereChain<T>> where(String operand) {
    writeJoins();
    return new StringOperatorBuilder<>(new JpqlBuilderWhereChain<>(builder), operand);
  }

  public StringOperatorBuilder<JpqlBuilderWhereChain<T>> where(UnaryOperator<String> operator) {
    writeJoins();
    return new StringOperatorBuilder<>(new JpqlBuilderWhereChain<>(builder), operator);
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
    joins.forEach(join -> join.writeTo(builder));
  }

  public Map<String, Object> getParameters() {
    return builder.getParameters();
  }

  public T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }
}
