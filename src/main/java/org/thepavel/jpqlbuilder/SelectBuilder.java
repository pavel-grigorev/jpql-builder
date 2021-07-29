/*
 * Copyright (c) 2020-2021 Pavel Grigorev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thepavel.jpqlbuilder;

import org.thepavel.jpqlbuilder.path.PathResolver;
import org.thepavel.jpqlbuilder.path.PathResolverList;
import org.thepavel.jpqlbuilder.proxy.ProxyClassHelper;
import org.thepavel.jpqlbuilder.query.JoinClause;
import org.thepavel.jpqlbuilder.query.JoinType;
import org.thepavel.jpqlbuilder.query.SelectQuery;
import org.thepavel.jpqlbuilder.querystring.JpqlStringBuilder;
import org.thepavel.jpqlbuilder.utils.AliasGenerator;
import org.thepavel.jpqlbuilder.utils.EntityHelper;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

public class SelectBuilder {
  private final AliasGenerator aliasGenerator = new AliasGenerator();
  private final PathResolverList rootPathResolvers = new PathResolverList();
  private final PathResolverList joinedPathResolvers = new PathResolverList();
  private final Map<Object, Join<?>> joins = new IdentityHashMap<>();
  private final SelectQuery query = new SelectQuery();
  private final JpqlBuilderContext context;
  private final JpqlStringBuilder stringBuilder;
  private boolean isSelectBuilt;

  SelectBuilder(JpqlBuilderContext context) {
    this.context = context;

    stringBuilder = new JpqlStringBuilder(rootPathResolvers, joinedPathResolvers);
  }

  public Select select(Object... things) {
    checkSelectNotBuilt();
    return new Select(stringBuilder, query).select(things);
  }

  public <T> OneLinerSelect<T> select(Class<T> entityType) {
    checkSelectNotBuilt();
    return new OneLinerSelect<>(stringBuilder, query, from(entityType));
  }

  private void checkSelectNotBuilt() {
    if (isSelectBuilt) {
      throw new IllegalStateException("Must not call the select method twice on the same builder instance");
    }
    isSelectBuilt = true;
  }

  public <T> T from(Class<T> entityType) {
    requireEntityClass(entityType);

    String alias = aliasGenerator.next();
    query.addFrom(entityType, alias);
    return addRoot(entityType, alias);
  }

  private static void requireEntityClass(Class<?> entityClass) {
    if (!EntityHelper.isEntity(entityClass)) {
      throw new IllegalArgumentException("Class " + entityClass.getName() + " is not an entity class");
    }
  }

  private <T> T addRoot(Class<T> entityType, String alias) {
    PathResolver<T> pathResolver = new PathResolver<>(entityType, alias, context);
    rootPathResolvers.add(pathResolver);
    return pathResolver.getPathSpecifier();
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
    Class<?> clazz = path.getClass();

    if (ProxyClassHelper.isProxyClass(clazz)) {
      return join(path, ProxyClassHelper.getTargetClass(clazz), type);
    }

    return join(path, clazz, type);
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
    if (joins.containsKey(joinedThing)) {
      return (Join<P>) joins.get(joinedThing);
    }

    if (isMap(targetClass)) {
      requireNonEmptyMap(joinedThing);
    } else {
      requireEntityClass(targetClass);
    }

    String alias = getNextAlias(type);
    JoinClause joinClause = createJoinClause(alias, joinedThing, type);
    PathResolver<P> pathResolver = createPathResolver(joinedThing, targetClass, alias);
    Join<P> join = new Join<>(joinClause, pathResolver);

    joinedPathResolvers.add(pathResolver);
    joins.put(joinedThing, join);

    return join;
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

  private JoinClause createJoinClause(String alias, Object joinedThing, JoinType type) {
    JoinClause joinClause = new JoinClause(alias, joinedThing, type);
    query.addJoin(joinClause);
    return joinClause;
  }

  @SuppressWarnings("unchecked")
  private <P> PathResolver<P> createPathResolver(Object joinedThing, Class<?> targetClass, String alias) {
    if (isMap(targetClass)) {
      return new PathResolver<>((Map<Object, Object>) joinedThing, alias, context);
    }
    return new PathResolver<>((Class<P>) targetClass, alias, context);
  }
}
