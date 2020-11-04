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

package org.thepavel.jpqlbuilder.samples;

import org.junit.Test;
import org.thepavel.jpqlbuilder.Select;
import org.thepavel.jpqlbuilder.Where;
import org.thepavel.jpqlbuilder.model.Company;
import org.thepavel.jpqlbuilder.model.Employee;
import org.thepavel.jpqlbuilder.model.Status;
import org.thepavel.jpqlbuilder.JpqlBuilder;
import org.thepavel.jpqlbuilder.operators.builders.ExpressionChain;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class DynamicQueryTest {
  @Test
  public void dynamicQuery() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Employee employee = builder.from(Employee.class);

    Where where = builder.select(employee).where(employee.getStatus()).isNot(Status.DELETED);
    where.and(employee.getName()).like("%test%");

    ExpressionChain idFilter = $(employee.getId()).is(1L);
    idFilter.or(employee.getId()).is(2L);
    idFilter.or(employee.getId()).is(3L);

    where.and(idFilter);

    where.orderBy(employee.getId()).asc().orderBy(employee.getName()).desc();

    String expected = "select a from test_Employee a " +
        "where a.status <> :a " +
        "and a.name like :b " +
        "and (a.id = :c or a.id = :d or a.id = :e) " +
        "order by a.id asc, a.name desc";

    assertEquals(expected, where.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", "%test%");
          put("c", 1L);
          put("d", 2L);
          put("e", 3L);
        }},
        where.getParameters()
    );
  }

  @Test
  public void dynamicQuery1() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);

    Select select = builder.select(c);
    Where where = null;

    for (String name : Arrays.asList("Google", "Apple")) {
      if (where == null) {
        where = select.where(c.getName()).is(name);
      } else {
        where.or(c.getName()).is(name);
      }
    }

    builder.join(c.getDepartments()).on(d -> $(d.getStatus()).isNot(Status.DELETED));

    String expected = "select a from test_Company a " +
        "join a.departments b on b.status <> :a " +
        "where a.name = :b or a.name = :c";

    assertEquals(expected, select.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", "Google");
          put("c", "Apple");
        }},
        select.getParameters()
    );
  }

  @Test
  public void dynamicQuery2() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);

    Select select = builder.select(c);
    ExpressionChain condition = null;

    for (String name : Arrays.asList("Google", "Apple")) {
      if (condition == null) {
        condition = $(c.getName()).is(name);
      } else {
        condition.or(c.getName()).is(name);
      }
    }

    builder.join(c.getDepartments()).on(d -> $(d.getStatus()).isNot(Status.DELETED));
    select.where(condition).and(c.getStatus()).isNot(Status.DELETED);

    String expected = "select a from test_Company a " +
        "join a.departments b on b.status <> :a " +
        "where (a.name = :b or a.name = :c) and a.status <> :d";

    assertEquals(expected, select.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", "Google");
          put("c", "Apple");
          put("d", Status.DELETED);
        }},
        select.getParameters()
    );
  }
}
