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

import org.junit.Test;
import org.thepavel.jpqlbuilder.DummyJpqlStringWriter;
import org.thepavel.jpqlbuilder.model.Company;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.DummyOperator.dummy;

public class SelectQueryTest {
  @Test
  public void minimal() {
    SelectQuery select = new SelectQuery();
    select.addSelected("a");
    select.addFrom(Company.class, "a");

    assertEquals("select a from Company a", DummyJpqlStringWriter.asString(select));
  }

  @Test
  public void oneJoin() {
    SelectQuery select = new SelectQuery();
    select.addSelected("a");
    select.addFrom(Company.class, "a");
    select.addJoin(new JoinClause("b", "a.departments", JoinType.INNER));

    assertEquals("select a from Company a join a.departments b", DummyJpqlStringWriter.asString(select));
  }

  @Test
  public void multipleJoins() {
    SelectQuery select = new SelectQuery();
    select.addSelected("a");
    select.addFrom(Company.class, "a");
    select.addJoin(new JoinClause("b", "a.departments", JoinType.INNER));
    select.addJoin(new JoinClause("c", "b.employees", JoinType.INNER));

    assertEquals("select a from Company a join a.departments b join b.employees c", DummyJpqlStringWriter.asString(select));
  }

  @Test
  public void where() {
    SelectQuery select = new SelectQuery();
    select.addSelected("a");
    select.addFrom(Company.class, "a");
    select.setWhere(dummy("A"));

    assertEquals("select a from Company a where dummy(A)", DummyJpqlStringWriter.asString(select));
  }

  @Test
  public void oneOrderBy() {
    SelectQuery select = new SelectQuery();
    select.addSelected("a");
    select.addFrom(Company.class, "a");
    select.addOrderBy("a.name");

    assertEquals("select a from Company a order by a.name", DummyJpqlStringWriter.asString(select));
  }

  @Test
  public void oneOrderByAsc() {
    SelectQuery select = new SelectQuery();
    select.addSelected("a");
    select.addFrom(Company.class, "a");
    select.addOrderBy("a.name");
    select.setOrderAsc();

    assertEquals("select a from Company a order by a.name asc", DummyJpqlStringWriter.asString(select));
  }

  @Test
  public void oneOrderByDesc() {
    SelectQuery select = new SelectQuery();
    select.addSelected("a");
    select.addFrom(Company.class, "a");
    select.addOrderBy("a.name");
    select.setOrderDesc();

    assertEquals("select a from Company a order by a.name desc", DummyJpqlStringWriter.asString(select));
  }

  @Test
  public void oneOrderByNullsFirst() {
    SelectQuery select = new SelectQuery();
    select.addSelected("a");
    select.addFrom(Company.class, "a");
    select.addOrderBy("a.name");
    select.setOrderNullsFirst();

    assertEquals("select a from Company a order by a.name nulls first", DummyJpqlStringWriter.asString(select));
  }

  @Test
  public void oneOrderByNullsLast() {
    SelectQuery select = new SelectQuery();
    select.addSelected("a");
    select.addFrom(Company.class, "a");
    select.addOrderBy("a.name");
    select.setOrderNullsLast();

    assertEquals("select a from Company a order by a.name nulls last", DummyJpqlStringWriter.asString(select));
  }

  @Test
  public void multipleOrderBy() {
    SelectQuery select = new SelectQuery();
    select.addSelected("a");
    select.addFrom(Company.class, "a");
    select.addOrderBy("a.name");
    select.setOrderDesc();
    select.setOrderNullsFirst();
    select.addOrderBy("a.id");
    select.setOrderAsc();
    select.setOrderNullsLast();
    select.addOrderBy("a.createTime");

    assertEquals("select a from Company a order by a.name desc nulls first, a.id asc nulls last, a.createTime", DummyJpqlStringWriter.asString(select));
  }

  @Test
  public void all() {
    SelectQuery select = new SelectQuery();
    select.addSelected("a");
    select.addFrom(Company.class, "a");
    select.addJoin(new JoinClause("b", "a.departments", JoinType.LEFT));
    select.addJoin(new JoinClause("c", "b.employees", JoinType.LEFT));
    select.setWhere(dummy("A"));
    select.addOrderBy("a.name");
    select.setOrderDesc();
    select.setOrderNullsFirst();
    select.addOrderBy("a.id");

    assertEquals("select a from Company a left join a.departments b left join b.employees c where dummy(A) order by a.name desc nulls first, a.id", DummyJpqlStringWriter.asString(select));
  }
}
