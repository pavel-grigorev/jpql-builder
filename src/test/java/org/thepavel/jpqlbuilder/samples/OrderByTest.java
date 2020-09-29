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
import org.thepavel.jpqlbuilder.functions.Functions;
import org.thepavel.jpqlbuilder.model.Employee;
import org.thepavel.jpqlbuilder.model.Status;
import org.thepavel.jpqlbuilder.JpqlBuilder;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.functions.Functions.lower;
import static org.thepavel.jpqlbuilder.functions.Functions.upper;

public class OrderByTest {
  @Test
  public void orderBy() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();

    String query = select
        .orderBy(Functions.lower(employee.getName())).desc()
        .orderBy(employee.getId()).asc()
        .orderBy(employee.getStatus())
        .getQueryString();

    String expected = "select a from test_Employee a order by lower(a.name) desc, a.id asc, a.status";

    assertEquals(expected, query);
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void orderByWithNullsOrdering() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();

    String query = select
        .orderBy(Functions.upper(employee.getName())).desc().nullsFirst()
        .orderBy(employee.getId()).asc().nullsLast()
        .orderBy(employee.getDepartment())
        .getQueryString();

    String expected =
        "select a from test_Employee a order by upper(a.name) desc nulls first, a.id asc nulls last, a.department";

    assertEquals(expected, query);
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void whereAndOrderBy() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();

    String query = select
        .where(employee.getStatus()).isNot(Status.DELETED)
        .orderBy(employee.getName()).desc()
        .getQueryString();

    String expected = "select a from test_Employee a where a.status <> :a order by a.name desc";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        select.getParameters()
    );
  }
}
