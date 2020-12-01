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

import org.thepavel.jpqlbuilder.operators.Operator;
import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.ArrayList;
import java.util.List;

public class UpdateClause implements Operator {
  private final List<Object> fields = new ArrayList<>();
  private final List<Object> values = new ArrayList<>();
  private Class<?> entityClass;
  private String alias;

  public void setEntityClass(Class<?> entityClass, String alias) {
    this.entityClass = entityClass;
    this.alias = alias;
  }

  public void addUpdate(Object field, Object value) {
    fields.add(field);
    values.add(value);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("update ");
    stringWriter.appendValue(entityClass);
    stringWriter.appendString(" ");
    stringWriter.appendString(alias);
    appendUpdates(stringWriter);
  }

  private void appendUpdates(JpqlStringWriter stringWriter) {
    if (fields.isEmpty()) {
      return;
    }
    stringWriter.appendString(" set ");

    for (int i = 0; i < fields.size(); i++) {
      if (i > 0) {
        stringWriter.appendString(", ");
      }
      writeOperand(fields.get(i), stringWriter);
      stringWriter.appendString(" = ");
      writeOperand(values.get(i), stringWriter);
    }
  }
}
