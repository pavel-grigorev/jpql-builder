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

public class UpdateClause implements Operator {
  private Class<?> entityClass;
  private String alias;
  private List<Pair<Object, Object>> updates;

  public void setEntityClass(Class<?> entityClass, String alias) {
    this.entityClass = entityClass;
    this.alias = alias;
  }

  public void setUpdates(List<Pair<Object, Object>> updates) {
    this.updates = updates;
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
    if (updates == null || updates.isEmpty()) {
      return;
    }

    stringWriter.appendString(" set ");
    boolean first = true;

    for (Pair<Object, Object> entry : updates) {
      if (first) {
        first = false;
      } else {
        stringWriter.appendString(", ");
      }
      writeOperand(entry.getKey(), stringWriter);
      stringWriter.appendString(" = ");
      writeOperand(entry.getValue(), stringWriter);
    }
  }
}
