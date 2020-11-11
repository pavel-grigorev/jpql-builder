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

package org.thepavel.jpqlbuilder.query;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.thepavel.jpqlbuilder.operators.Operator;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery implements Operator {
  private final SelectClause select = new SelectClause();
  private final List<JoinClause> joins = new ArrayList<>();
  private final WhereClause where = new WhereClause();
  private final GroupByClause groupBy = new GroupByClause();
  private final OrderByClause orderBy = new OrderByClause();

  public void addSelected(Object selected) {
    select.addSelected(selected);
  }

  public void addFrom(Class<?> type, String alias) {
    select.addFrom(type, alias);
  }

  public void addJoin(JoinClause join) {
    joins.add(join);
  }

  public void setWhere(Operator operator) {
    where.setOperator(operator);
  }

  public void addGroupBy(Object item) {
    groupBy.addItem(item);
  }

  public void addOrderBy(Object operand) {
    orderBy.addItem(operand);
  }

  public void setOrderAsc() {
    orderBy.setAsc();
  }

  public void setOrderDesc() {
    orderBy.setDesc();
  }

  public void setOrderNullsFirst() {
    orderBy.setNullsFirst();
  }

  public void setOrderNullsLast() {
    orderBy.setNullsLast();
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    select.writeTo(stringWriter);
    joins.forEach(join -> join.writeTo(stringWriter));
    where.writeTo(stringWriter);
    groupBy.writeTo(stringWriter);
    orderBy.writeTo(stringWriter);
  }
}
