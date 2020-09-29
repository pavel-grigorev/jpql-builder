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
import org.thepavel.jpqlbuilder.model.Employee;
import org.thepavel.jpqlbuilder.model.Status;
import org.thepavel.jpqlbuilder.JpqlBuilder;
import org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.not;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class WhereTest {
  @Test
  public void whereSimple() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();

    String query = select.where(employee.getName()).like("%test%").getQueryString();

    String expected = "select a from test_Employee a where a.name like :a";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "%test%");
        }},
        select.getParameters()
    );
  }

  @Test
  public void whereExpression() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();

    String query = select
        .where(
            $(employee.getName()).is("Test")
                .or(employee.getStatus()).is(Status.ACTIVE)
        )
        .and(employee.getId()).isNot(1L)
        .getQueryString();

    String expected = "select a from test_Employee a where (a.name = :a or a.status = :b) and a.id <> :c";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "Test");
          put("b", Status.ACTIVE);
          put("c", 1L);
        }},
        select.getParameters()
    );
  }

  @Test
  public void whereComplicated() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();

    String query = select
        .where(employee.getId()).isNot(123456L)
        .and(employee.getStatus()).isNot(Status.DELETED)
        .and(employee.getDepartment().getStatus()).isNot(Status.DELETED)
        .and(employee.getDepartment().getCompany().getStatus()).isNot(Status.DELETED)
        .and(employee.getDepartment().getName()).like("%test%")
        .or(employee.getName()).isNull()
        .or(employee.getDepartment().getId()).isNotNull()
        .and(
            $(employee.getDepartment().getName()).notLike("A")
                .or(employee.getDepartment().getCompany().getName()).like("10\\%", "\\")
                .or(not(
                    OperatorBuilder.$(employee.getStatus()).is(Status.ACTIVE)
                        .and(employee.getName()).like("B")
                ))
                .and(employee.getId()).between(10L, 20L)
                .and(
                    OperatorBuilder.$(employee.getStatus()).in(Status.ACTIVE, Status.SUSPENDED)
                        .or(employee.getStatus()).notIn(Status.DELETED, Status.DISABLED)
                )
        )
        .or(
            $(employee.getId()).greaterThanOrEqual(0L)
                .and(employee.getId()).lessThanOrEqual(100L)
        )
        .getQueryString();

    String expected = "select a from test_Employee a " +
        "where a.id <> :a " +
        "and a.status <> :b " +
        "and a.department.status <> :c " +
        "and a.department.company.status <> :d " +
        "and a.department.name like :e " +
        "or a.name is null " +
        "or a.department.id is not null " +
        "and (" +
          "a.department.name not like :f " +
          "or a.department.company.name like :g escape :h " +
          "or (not (a.status = :i and a.name like :j)) " +
          "and a.id between :k and :l " +
          "and (a.status in :m or a.status not in :n)" +
        ") " +
        "or (a.id >= :o and a.id <= :p)";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 123456L);
          put("b", Status.DELETED);
          put("c", Status.DELETED);
          put("d", Status.DELETED);
          put("e", "%test%");
          put("f", "A");
          put("g", "10\\%");
          put("h", "\\");
          put("i", Status.ACTIVE);
          put("j", "B");
          put("k", 10L);
          put("l", 20L);
          put("m", Arrays.asList(Status.ACTIVE, Status.SUSPENDED));
          put("n", Arrays.asList(Status.DELETED, Status.DISABLED));
          put("o", 0L);
          put("p", 100L);
        }},
        select.getParameters()
    );
  }
}
