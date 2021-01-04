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

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.thepavel.jpqlbuilder.operators.Operator;

import java.util.ArrayList;
import java.util.List;

public class OrderByClause implements Operator {
  private final List<Item> items = new ArrayList<>();

  public void addItem(Object operand) {
    Item item = new Item();
    item.operand = operand;
    items.add(item);
  }

  public void setAsc() {
    getLastItem().ordering = " asc";
  }

  public void setDesc() {
    getLastItem().ordering = " desc";
  }

  public void setNullsFirst() {
    getLastItem().nullsOrdering = " nulls first";
  }

  public void setNullsLast() {
    getLastItem().nullsOrdering = " nulls last";
  }

  private Item getLastItem() {
    return items.get(items.size() - 1);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    if (items.isEmpty()) {
      return;
    }
    stringWriter.appendString(" order by ");
    writeItems(stringWriter);
  }

  private void writeItems(JpqlStringWriter stringWriter) {
    for (int i = 0; i < items.size(); i++) {
      if (i > 0) {
        stringWriter.appendString(", ");
      }
      writeItem(items.get(i), stringWriter);
    }
  }

  private void writeItem(Item item, JpqlStringWriter stringWriter) {
    writeOperand(item.operand, stringWriter);

    if (item.ordering != null) {
      stringWriter.appendString(item.ordering);
    }

    if (item.nullsOrdering != null) {
      stringWriter.appendString(item.nullsOrdering);
    }
  }

  private static class Item {
    private Object operand;
    private String ordering;
    private String nullsOrdering;
  }
}
