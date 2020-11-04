/*
 * Copyright (c) 2020 Pavel Grigorev.
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

package org.thepavel.jpqlbuilder.querystring;

import org.thepavel.jpqlbuilder.utils.AliasGenerator;
import org.thepavel.jpqlbuilder.path.PathResolverList;
import org.thepavel.jpqlbuilder.query.SelectQuery;
import org.thepavel.jpqlbuilder.utils.EntityHelper;

import java.util.HashMap;
import java.util.Map;

public class JpqlStringBuilder implements JpqlStringWriter {
  private final PathResolverList roots;
  private final PathResolverList joins;
  private final StringBuilder builder = new StringBuilder();
  private final Map<String, Object> parameters = new HashMap<>();
  private final AliasGenerator aliasGenerator = new AliasGenerator();

  public JpqlStringBuilder(PathResolverList roots, PathResolverList joins) {
    this.roots = roots;
    this.joins = joins;
  }

  @Override
  public String toString() {
    return builder.toString();
  }

  public String build(SelectQuery query) {
    builder.delete(0, builder.length());
    parameters.clear();
    aliasGenerator.reset();

    query.writeTo(this);
    return toString();
  }

  public Map<String, Object> getParameters() {
    return new HashMap<>(parameters);
  }

  @Override
  public void appendString(String string) {
    builder.append(string);
  }

  @Override
  public void appendValue(Object value) {
    if (isEntityClass(value)) {
      builder.append(getEntityName(value));
      return;
    }
    String path = getPropertyPath(value);
    if (path != null) {
      builder.append(path);
      return;
    }
    appendParameter(value);
  }

  private static boolean isEntityClass(Object value) {
    return value instanceof Class && EntityHelper.isEntity((Class<?>) value);
  }

  private static String getEntityName(Object value) {
    return EntityHelper.getEntityName((Class<?>) value);
  }

  private String getPropertyPath(Object value) {
    String path = roots.getPropertyPath(value);
    return path != null ? path : joins.getPropertyPath(value);
  }

  private void appendParameter(Object value) {
    String alias = aliasGenerator.next();
    parameters.put(alias, value);
    builder.append(':').append(alias);
  }
}
