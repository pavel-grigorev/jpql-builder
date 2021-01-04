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

import org.thepavel.jpqlbuilder.functions.JpqlFunction;
import org.thepavel.jpqlbuilder.operators.Operator;
import org.thepavel.jpqlbuilder.operators.Parentheses;
import org.thepavel.jpqlbuilder.operators.builders.CollectionOperatorBuilder;
import org.thepavel.jpqlbuilder.operators.builders.ExpressionChain;
import org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder;
import org.thepavel.jpqlbuilder.query.SelectQuery;
import org.thepavel.jpqlbuilder.querystring.JpqlStringBuilder;

import java.util.Collection;
import java.util.Map;

public class GroupBy implements JpqlQuery {
  private final JpqlStringBuilder stringBuilder;
  private final SelectQuery query;

  GroupBy(Object item, JpqlStringBuilder stringBuilder, SelectQuery query) {
    this.stringBuilder = stringBuilder;
    this.query = query;

    query.addGroupBy(item);
  }

  public GroupBy groupBy(Object item) {
    query.addGroupBy(item);
    return this;
  }

  public <T> OperatorBuilder<T, Having> having(T operand) {
    return new OperatorBuilder<>(createHaving(), operand);
  }

  public <T> OperatorBuilder<T, Having> having(JpqlFunction<T> operator) {
    return new OperatorBuilder<>(createHaving(), operator);
  }

  public CollectionOperatorBuilder<Having> having(Collection<?> operand) {
    return new CollectionOperatorBuilder<>(createHaving(), operand);
  }

  public Having having(ExpressionChain chain) {
    return createHaving(new Parentheses(chain.getOperator()));
  }

  private Having createHaving() {
    return new Having(stringBuilder, query);
  }

  private Having createHaving(Operator operator) {
    return new Having(operator, stringBuilder, query);
  }

  public OrderBy orderBy(Object operand) {
    return new OrderBy(operand, stringBuilder, query);
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
