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
import org.thepavel.jpqlbuilder.operators.builders.BaseUpdateChain;
import org.thepavel.jpqlbuilder.operators.builders.CollectionOperatorBuilder;
import org.thepavel.jpqlbuilder.operators.builders.ExpressionChain;
import org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder;
import org.thepavel.jpqlbuilder.operators.builders.WhereChain;
import org.thepavel.jpqlbuilder.query.UpdateQuery;
import org.thepavel.jpqlbuilder.querystring.JpqlStringBuilder;

import java.util.Collection;
import java.util.Map;

public class Update extends BaseUpdateChain<Update> implements JpqlQuery {
  private final JpqlStringBuilder stringBuilder;
  private final UpdateQuery query;

  Update(JpqlStringBuilder stringBuilder, UpdateQuery query) {
    this.stringBuilder = stringBuilder;
    this.query = query;

    query.setUpdates(getUpdates());
  }

  public <T> OperatorBuilder<T, WhereChain> where(T operand) {
    return new OperatorBuilder<>(createWhere(), operand);
  }

  public <T> OperatorBuilder<T, WhereChain> where(JpqlFunction<T> operator) {
    return new OperatorBuilder<>(createWhere(), operator);
  }

  public CollectionOperatorBuilder<WhereChain> where(Collection<?> operand) {
    return new CollectionOperatorBuilder<>(createWhere(), operand);
  }

  public WhereChain where(ExpressionChain chain) {
    return createWhere(new Parentheses(chain.getOperator()));
  }

  private WhereChain createWhere() {
    return new WhereChain(stringBuilder, query);
  }

  private WhereChain createWhere(Operator operator) {
    return new WhereChain(operator, stringBuilder, query);
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
