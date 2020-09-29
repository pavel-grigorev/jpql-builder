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

package org.thepavel.jpqlbuilder;

import org.thepavel.jpqlbuilder.query.SelectQuery;
import org.thepavel.jpqlbuilder.querystring.JpqlStringBuilder;
import org.thepavel.jpqlbuilder.path.PathResolver;

import java.util.Map;
import java.util.function.Function;

public class OrderBy<T> implements JpqlQuery {
  private final PathResolver<T> pathResolver;
  private final JpqlStringBuilder stringBuilder;
  private final SelectQuery query;

  OrderBy(Object operand, PathResolver<T> pathResolver, JpqlStringBuilder stringBuilder, SelectQuery query) {
    this.pathResolver = pathResolver;
    this.stringBuilder = stringBuilder;
    this.query = query;

    query.addOrderBy(operand);
  }

  public OrderBy<T> orderBy(Object operand) {
    query.addOrderBy(operand);
    return this;
  }

  public OrderBy<T> orderBy(Function<T, Object> operandFunction) {
    return orderBy(operandFunction.apply(getPathSpecifier()));
  }

  private T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }

  public OrderBy<T> asc() {
    query.setOrderAsc();
    return this;
  }

  public OrderBy<T> desc() {
    query.setOrderDesc();
    return this;
  }

  public OrderBy<T> nullsFirst() {
    query.setOrderNullsFirst();
    return this;
  }

  public OrderBy<T> nullsLast() {
    query.setOrderNullsLast();
    return this;
  }

  @Override
  public String getQueryString() {
    return stringBuilder.build(query);
  }

  @Override
  public Map<String, Object> getParameters() {
    return stringBuilder.getParameters();
  }
}
