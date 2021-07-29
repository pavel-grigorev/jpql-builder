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

package org.thepavel.jpqlbuilder.query;

import org.thepavel.jpqlbuilder.operators.Operator;
import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.thepavel.jpqlbuilder.utils.Pair;

import java.util.List;

public class UpdateQuery implements Query {
  private final UpdateClause update = new UpdateClause();
  private final WhereClause where = new WhereClause();

  public void setEntityClass(Class<?> entityClass, String alias) {
    update.setEntityClass(entityClass, alias);
  }

  public void setUpdates(List<Pair<Object, Object>> updates) {
    update.setUpdates(updates);
  }

  @Override
  public void setWhere(Operator operator) {
    where.setOperator(operator);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    update.writeTo(stringWriter);
    where.writeTo(stringWriter);
  }
}
