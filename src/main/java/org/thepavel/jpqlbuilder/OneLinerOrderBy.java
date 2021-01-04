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

import org.thepavel.jpqlbuilder.query.SelectQuery;
import org.thepavel.jpqlbuilder.querystring.JpqlStringBuilder;

import java.util.Map;
import java.util.function.Function;

public class OneLinerOrderBy<T> implements JpqlQuery {
  private final JpqlStringBuilder stringBuilder;
  private final SelectQuery query;
  private final T rootPathSpecifier;

  OneLinerOrderBy(JpqlStringBuilder stringBuilder, SelectQuery query, T rootPathSpecifier) {
    this.stringBuilder = stringBuilder;
    this.query = query;
    this.rootPathSpecifier = rootPathSpecifier;
  }

  public OneLinerOrderBy<T> orderBy(Function<T, Object> operandFunction) {
    query.addOrderBy(operandFunction.apply(rootPathSpecifier));
    return this;
  }

  public OneLinerOrderBy<T> asc() {
    query.setOrderAsc();
    return this;
  }

  public OneLinerOrderBy<T> desc() {
    query.setOrderDesc();
    return this;
  }

  public OneLinerOrderBy<T> nullsFirst() {
    query.setOrderNullsFirst();
    return this;
  }

  public OneLinerOrderBy<T> nullsLast() {
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
