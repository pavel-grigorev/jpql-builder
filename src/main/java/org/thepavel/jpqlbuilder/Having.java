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

import org.thepavel.jpqlbuilder.operators.Operator;
import org.thepavel.jpqlbuilder.operators.builders.BaseExpressionChain;
import org.thepavel.jpqlbuilder.query.SelectQuery;
import org.thepavel.jpqlbuilder.querystring.JpqlStringBuilder;

import java.util.Map;

public class Having extends BaseExpressionChain<Having> implements JpqlQuery {
  private final JpqlStringBuilder stringBuilder;
  private final SelectQuery query;

  Having(JpqlStringBuilder stringBuilder, SelectQuery query) {
    this.stringBuilder = stringBuilder;
    this.query = query;
  }

  Having(Operator operator, JpqlStringBuilder stringBuilder, SelectQuery query) {
    super(operator);
    this.stringBuilder = stringBuilder;
    this.query = query;

    query.setHaving(operator);
  }

  @Override
  protected void onOperatorChange(Operator operator) {
    query.setHaving(operator);
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
