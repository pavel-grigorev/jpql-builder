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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.DummyJpqlStringWriter.asString;

public class OrderByClauseTest {
  @Test
  public void empty() {
    assertEquals("", asString(new OrderByClause()));
  }

  @Test
  public void oneItem() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");

    assertEquals(" order by A", asString(orderBy));
  }

  @Test
  public void multipleItems() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.addItem("B");
    orderBy.addItem("C");

    assertEquals(" order by A, B, C", asString(orderBy));
  }

  @Test
  public void asc() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setAsc();

    assertEquals(" order by A asc", asString(orderBy));
  }

  @Test
  public void desc() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();

    assertEquals(" order by A desc", asString(orderBy));
  }

  @Test
  public void multipleCallsToAscAndDesc() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setAsc();
    orderBy.setDesc();

    assertEquals(" order by A desc", asString(orderBy));
  }

  @Test
  public void nullsFirst() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setNullsFirst();

    assertEquals(" order by A nulls first", asString(orderBy));
  }

  @Test
  public void nullsLast() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setNullsLast();

    assertEquals(" order by A nulls last", asString(orderBy));
  }

  @Test
  public void multipleCallsToNullsFirstAndNullsLast() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setNullsFirst();
    orderBy.setNullsLast();

    assertEquals(" order by A nulls last", asString(orderBy));
  }

  @Test
  public void ascNullsFirst() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setAsc();
    orderBy.setNullsFirst();

    assertEquals(" order by A asc nulls first", asString(orderBy));
  }

  @Test
  public void ascNullsLast() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setAsc();
    orderBy.setNullsLast();

    assertEquals(" order by A asc nulls last", asString(orderBy));
  }

  @Test
  public void descNullsFirst() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();
    orderBy.setNullsFirst();

    assertEquals(" order by A desc nulls first", asString(orderBy));
  }

  @Test
  public void descNullsLast() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();
    orderBy.setNullsLast();

    assertEquals(" order by A desc nulls last", asString(orderBy));
  }

  @Test
  public void multipleItemsWithOrdering() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();
    orderBy.addItem("B");
    orderBy.setAsc();
    orderBy.addItem("C");

    assertEquals(" order by A desc, B asc, C", asString(orderBy));
  }

  @Test
  public void multipleItemsWithNullsOrdering() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setNullsLast();
    orderBy.addItem("B");
    orderBy.setNullsFirst();
    orderBy.addItem("C");

    assertEquals(" order by A nulls last, B nulls first, C", asString(orderBy));
  }

  @Test
  public void multipleItemsWithOrderingAndNullsOrdering() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();
    orderBy.setNullsFirst();
    orderBy.addItem("B");
    orderBy.setAsc();
    orderBy.setNullsLast();
    orderBy.addItem("C");

    assertEquals(" order by A desc nulls first, B asc nulls last, C", asString(orderBy));
  }
}
