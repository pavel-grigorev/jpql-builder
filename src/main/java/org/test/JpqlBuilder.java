package org.test;

import org.springframework.aop.support.AopUtils;
import org.test.factory.CollectionInstanceFactory;
import org.test.factory.DefaultCollectionInstanceFactory;
import org.test.factory.DefaultInstanceFactory;
import org.test.factory.DefaultMapInstanceFactory;
import org.test.factory.DefaultProxyFactory;
import org.test.factory.InstanceFactory;
import org.test.factory.MapInstanceFactory;
import org.test.factory.ProxyFactory;
import org.test.functions.JpqlFunction;
import org.test.operators.Operator;
import org.test.operators.Parentheses;
import org.test.operators.builders.ExpressionChain;
import org.test.operators.builders.OperatorBuilder;
import org.test.path.PathResolver;
import org.test.path.PathResolverList;
import org.test.query.JoinClause;
import org.test.query.JoinType;
import org.test.query.SelectQuery;
import org.test.querystring.JpqlStringBuilder;
import org.test.utils.AliasGenerator;
import org.test.utils.EntityHelper;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class JpqlBuilder<T> implements JpqlQuery {
  private final AliasGenerator aliasGenerator = new AliasGenerator();
  private final PathResolverList joinedPathResolvers = new PathResolverList();
  private final JpqlBuilderContext context;
  private final PathResolver<T> pathResolver;
  private final JpqlStringBuilder stringBuilder;
  private final SelectQuery query;

  private JpqlBuilder(Class<T> entityClass, JpqlBuilderContext context) {
    requireEntityClass(entityClass);
    this.context = context;

    String rootAlias = aliasGenerator.next();

    pathResolver = new PathResolver<>(entityClass, rootAlias, context);
    stringBuilder = new JpqlStringBuilder(pathResolver, joinedPathResolvers);
    query = new SelectQuery(rootAlias, entityClass);
  }

  private static void requireEntityClass(Class<?> entityClass) {
    if (!EntityHelper.isEntity(entityClass)) {
      throw new IllegalArgumentException("Class " + entityClass.getName() + " is not an entity class");
    }
  }

  public static Builder with(InstanceFactory instanceFactory) {
    return new Builder(instanceFactory);
  }

  public static Builder with(CollectionInstanceFactory collectionInstanceFactory) {
    return new Builder(collectionInstanceFactory);
  }

  public static Builder with(MapInstanceFactory mapInstanceFactory) {
    return new Builder(mapInstanceFactory);
  }

  public static Builder with(ProxyFactory proxyFactory) {
    return new Builder(proxyFactory);
  }

  public static <T> JpqlBuilder<T> select(Class<T> entityType) {
    return new JpqlBuilder<>(entityType, JpqlBuilderContext.defaultContext());
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

  // TODO: refactor into JoinFactory
  @SuppressWarnings("unchecked")
  private <P> Join<P> join(Object joinedThing, Class<?> targetClass, JoinType type) {
    boolean isMap = isMap(targetClass);

    if (isMap) {
      requireNonEmptyMap(joinedThing);
    } else {
      requireEntityClass(targetClass);
    }

    String alias = getNextAlias(type);
    PathResolver<P> pathResolver;

    if (isMap) {
      pathResolver = new PathResolver<>((Map<Object, Object>) joinedThing, alias, context);
    } else {
      pathResolver = new PathResolver<>((Class<P>) targetClass, alias, context);
    }

    joinedPathResolvers.add(pathResolver);

    JoinClause joinClause = new JoinClause(alias, joinedThing, type);
    query.addJoin(joinClause);

    return new Join<>(joinClause, pathResolver);
  }

  private static boolean isMap(Class<?> targetClass) {
    return Map.class.isAssignableFrom(targetClass);
  }

  @SuppressWarnings("rawtypes")
  private static void requireNonEmptyMap(Object object) {
    if (!(object instanceof Map) || ((Map) object).isEmpty()) {
      throw new IllegalArgumentException("can not join an empty map");
    }
  }

  private String getNextAlias(JoinType type) {
    return type.hasAlias() ? aliasGenerator.next() : "";
  }

  public <P> OperatorBuilder<P, Where<T>> where(P operand) {
    return new OperatorBuilder<>(createWhere(), operand);
  }

  public <P> OperatorBuilder<P, Where<T>> where(JpqlFunction<P> operator) {
    return new OperatorBuilder<>(createWhere(), operator);
  }

  public Where<T> where(ExpressionChain chain) {
    return createWhere(new Parentheses(chain.getOperator()));
  }

  public Where<T> where(Function<T, ExpressionChain> chainFunction) {
    return createWhere(chainFunction.apply(getPathSpecifier()).getOperator());
  }

  private Where<T> createWhere() {
    return new Where<>(pathResolver, stringBuilder, query);
  }

  private Where<T> createWhere(Operator operator) {
    return new Where<>(operator, pathResolver, stringBuilder, query);
  }

  public OrderBy<T> orderBy(Object operand) {
    return new OrderBy<>(operand, pathResolver, stringBuilder, query);
  }

  public OrderBy<T> orderBy(Function<T, Object> operandFunction) {
    return orderBy(operandFunction.apply(getPathSpecifier()));
  }

  @Override
  public String getQueryString() {
    return stringBuilder.build(query);
  }

  @Override
  public Map<String, Object> getParameters() {
    return stringBuilder.getParameters();
  }

  public T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }

  public static class Builder {
    private InstanceFactory instanceFactory;
    private CollectionInstanceFactory collectionInstanceFactory;
    private MapInstanceFactory mapInstanceFactory;
    private ProxyFactory proxyFactory;

    private Builder(InstanceFactory instanceFactory) {
      this.instanceFactory = instanceFactory;
    }

    private Builder(CollectionInstanceFactory collectionInstanceFactory) {
      this.collectionInstanceFactory = collectionInstanceFactory;
    }

    private Builder(MapInstanceFactory mapInstanceFactory) {
      this.mapInstanceFactory = mapInstanceFactory;
    }

    private Builder(ProxyFactory proxyFactory) {
      this.proxyFactory = proxyFactory;
    }

    public Builder with(InstanceFactory instanceFactory) {
      this.instanceFactory = instanceFactory;
      return this;
    }

    public Builder with(CollectionInstanceFactory collectionInstanceFactory) {
      this.collectionInstanceFactory = collectionInstanceFactory;
      return this;
    }

    public Builder with(MapInstanceFactory mapInstanceFactory) {
      this.mapInstanceFactory = mapInstanceFactory;
      return this;
    }

    public Builder with(ProxyFactory proxyFactory) {
      this.proxyFactory = proxyFactory;
      return this;
    }

    public <T> JpqlBuilder<T> select(Class<T> entityType) {
      return new JpqlBuilder<>(entityType, createContext());
    }

    private JpqlBuilderContext createContext() {
      if (instanceFactory == null) {
        instanceFactory = new DefaultInstanceFactory();
      }
      if (collectionInstanceFactory == null) {
        collectionInstanceFactory = new DefaultCollectionInstanceFactory();
      }
      if (mapInstanceFactory == null) {
        mapInstanceFactory = new DefaultMapInstanceFactory();
      }
      if (proxyFactory == null) {
        proxyFactory = new DefaultProxyFactory();
      }
      return new JpqlBuilderContext(instanceFactory, collectionInstanceFactory, mapInstanceFactory, proxyFactory);
    }
  }
}
