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
import org.thepavel.jpqlbuilder.query.DeleteQuery;
import org.thepavel.jpqlbuilder.querystring.JpqlStringBuilder;
import org.thepavel.jpqlbuilder.utils.AliasGenerator;
import org.thepavel.jpqlbuilder.utils.EntityHelper;

public class DeleteBuilder {
  private final AliasGenerator aliasGenerator = new AliasGenerator();
  private final PathResolverList rootPathResolvers = new PathResolverList();
  private final DeleteQuery query = new DeleteQuery();
  private final JpqlBuilderContext context;
  private final JpqlStringBuilder stringBuilder;
  private boolean isDeleteBuilt;
  private boolean isFromCalled;

  DeleteBuilder(JpqlBuilderContext context) {
    this.context = context;

    stringBuilder = new JpqlStringBuilder(rootPathResolvers);
  }

  public <T> OneLinerDelete<T> delete(Class<T> entityClass) {
    checkDeleteNotBuilt();
    return new OneLinerDelete<>(stringBuilder, query, from(entityClass));
  }

  public Delete delete(Object thing) {
    checkDeleteNotBuilt();
    checkDeletedThing(thing);
    return new Delete(stringBuilder, query);
  }

  private void checkDeleteNotBuilt() {
    if (isDeleteBuilt) {
      throw new IllegalStateException("Must not call the delete method twice on the same builder instance");
    }
    isDeleteBuilt = true;
  }

  private void checkDeletedThing(Object thing) {
    if (rootPathResolvers.getPropertyPath(thing) == null) {
      throw new IllegalArgumentException("An object returned by the from method is expected");
    }
  }

  public <T> T from(Class<T> entityType) {
    checkFromNotCalled();
    requireEntityClass(entityType);

    String alias = aliasGenerator.next();
    query.setFrom(entityType, alias);
    return addRoot(entityType, alias);
  }

  private void checkFromNotCalled() {
    if (isFromCalled) {
      throw new IllegalStateException("Must not call the from method twice on the same builder instance");
    }
    isFromCalled = true;
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
