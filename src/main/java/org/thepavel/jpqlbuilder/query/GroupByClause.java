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

import java.util.ArrayList;
import java.util.List;

public class GroupByClause implements Operator {
  private final List<Object> items = new ArrayList<>();

  public void addItem(Object item) {
    items.add(item);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    if (items.isEmpty()) {
      return;
    }
    stringWriter.appendString(" group by ");
    writeItems(stringWriter);
  }

  private void writeItems(JpqlStringWriter stringWriter) {
    for (int i = 0; i < items.size(); i++) {
      if (i > 0) {
        stringWriter.appendString(", ");
      }
      writeOperand(items.get(i), stringWriter);
    }
  }
}
