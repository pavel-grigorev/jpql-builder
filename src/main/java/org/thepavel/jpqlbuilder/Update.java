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
import org.thepavel.jpqlbuilder.operators.builders.BaseExpressionChain;
import org.thepavel.jpqlbuilder.operators.builders.BaseUpdateChain;
import org.thepavel.jpqlbuilder.operators.builders.CollectionOperatorBuilder;
import org.thepavel.jpqlbuilder.operators.builders.ExpressionChain;
import org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder;
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

  public <T> OperatorBuilder<T, Where> where(T operand) {
    return new OperatorBuilder<>(new Where(), operand);
  }

  public <T> OperatorBuilder<T, Where> where(JpqlFunction<T> operator) {
    return new OperatorBuilder<>(new Where(), operator);
  }

  public CollectionOperatorBuilder<Where> where(Collection<?> operand) {
    return new CollectionOperatorBuilder<>(new Where(), operand);
  }

  public Where where(ExpressionChain chain) {
    return new Where(new Parentheses(chain.getOperator()));
  }

  @Override
  public String getQueryString() {
    return stringBuilder.build(query);
  }

  @Override
  public Map<String, Object> getParameters() {
    return stringBuilder.getParameters();
  }

  public class Where extends BaseExpressionChain<Where> implements JpqlQuery {
    private Where() {
    }

    private Where(Operator operator) {
      super(operator);
      query.setWhere(operator);
    }

    @Override
    protected void onOperatorChange(Operator operator) {
      query.setWhere(operator);
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
