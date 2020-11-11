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

public class Select implements JpqlQuery {
  private final JpqlStringBuilder stringBuilder;
  private final SelectQuery query;

  Select(JpqlStringBuilder stringBuilder, SelectQuery query) {
    this.stringBuilder = stringBuilder;
    this.query = query;
  }

  public Select select(Object... things) {
    for (Object thing : things) {
      query.addSelected(thing);
    }
    return this;
  }

  public <T> OperatorBuilder<T, Where> where(T operand) {
    return new OperatorBuilder<>(createWhere(), operand);
  }

  public <T> OperatorBuilder<T, Where> where(JpqlFunction<T> operator) {
    return new OperatorBuilder<>(createWhere(), operator);
  }

  public CollectionOperatorBuilder<Where> where(Collection<?> operand) {
    return new CollectionOperatorBuilder<>(createWhere(), operand);
  }

  public Where where(ExpressionChain chain) {
    return createWhere(new Parentheses(chain.getOperator()));
  }

  private Where createWhere() {
    return new Where(stringBuilder, query);
  }

  private Where createWhere(Operator operator) {
    return new Where(operator, stringBuilder, query);
  }

  public GroupBy groupBy(Object item) {
    return new GroupBy(item, stringBuilder, query);
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
