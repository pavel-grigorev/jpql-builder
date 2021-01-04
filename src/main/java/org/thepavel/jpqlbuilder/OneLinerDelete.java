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

import org.thepavel.jpqlbuilder.operators.builders.ExpressionChain;
import org.thepavel.jpqlbuilder.query.DeleteQuery;
import org.thepavel.jpqlbuilder.querystring.JpqlStringBuilder;

import java.util.Map;
import java.util.function.Function;

public class OneLinerDelete<T> implements JpqlQuery {
  private final JpqlStringBuilder stringBuilder;
  private final DeleteQuery query;
  private final T rootPathSpecifier;

  OneLinerDelete(JpqlStringBuilder stringBuilder, DeleteQuery query, T rootPathSpecifier) {
    this.stringBuilder = stringBuilder;
    this.query = query;
    this.rootPathSpecifier = rootPathSpecifier;
  }

  public JpqlQuery where(Function<T, ExpressionChain> chainFunction) {
    query.setWhere(chainFunction.apply(rootPathSpecifier).getOperator());
    return new Query();
  }

  @Override
  public String getQueryString() {
    return stringBuilder.build(query);
  }

  @Override
  public Map<String, Object> getParameters() {
    return stringBuilder.getParameters();
  }

  public class Query implements JpqlQuery {
    private Query() {
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
}
