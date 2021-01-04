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
import org.thepavel.jpqlbuilder.query.UpdateQuery;
import org.thepavel.jpqlbuilder.querystring.JpqlStringBuilder;
import org.thepavel.jpqlbuilder.utils.AliasGenerator;
import org.thepavel.jpqlbuilder.utils.EntityHelper;

public class UpdateBuilder {
  private final AliasGenerator aliasGenerator = new AliasGenerator();
  private final PathResolverList rootPathResolvers = new PathResolverList();
  private final UpdateQuery query = new UpdateQuery();
  private final JpqlBuilderContext context;
  private final JpqlStringBuilder stringBuilder;
  private boolean isUpdateBuilt;
  private boolean isEntityCalled;

  UpdateBuilder(JpqlBuilderContext context) {
    this.context = context;

    stringBuilder = new JpqlStringBuilder(rootPathResolvers);
  }

  public <T> OneLinerUpdate<T> update(Class<T> entityClass) {
    checkUpdateNotBuilt();
    return new OneLinerUpdate<>(stringBuilder, query, entity(entityClass));
  }

  public Update update(Object thing) {
    checkUpdateNotBuilt();
    checkUpdatedThing(thing);
    return new Update(stringBuilder, query);
  }

  private void checkUpdateNotBuilt() {
    if (isUpdateBuilt) {
      throw new IllegalStateException("Must not call the update method twice on the same builder instance");
    }
    isUpdateBuilt = true;
  }

  private void checkUpdatedThing(Object thing) {
    if (rootPathResolvers.getPropertyPath(thing) == null) {
      throw new IllegalArgumentException("An object returned by the entity method is expected");
    }
  }

  public <T> T entity(Class<T> entityType) {
    checkEntityNotCalled();
    requireEntityClass(entityType);

    String alias = aliasGenerator.next();
    query.setEntityClass(entityType, alias);
    return addRoot(entityType, alias);
  }

  private void checkEntityNotCalled() {
    if (isEntityCalled) {
      throw new IllegalStateException("Must not call the entity method twice on the same builder instance");
    }
    isEntityCalled = true;
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
}
