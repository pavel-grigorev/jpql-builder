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
import org.thepavel.jpqlbuilder.Join;
import org.thepavel.jpqlbuilder.Select;
import org.thepavel.jpqlbuilder.model.Department;
import org.thepavel.jpqlbuilder.model.Employee;
import org.thepavel.jpqlbuilder.model.HeadOfDepartment;
import org.thepavel.jpqlbuilder.model.Status;
import org.thepavel.jpqlbuilder.JpqlBuilder;
import org.thepavel.jpqlbuilder.model.Company;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class JoinTest {
  @Test
  public void entityJoins() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Employee employee = builder.from(Employee.class);
    Department department = builder.join(employee.getDepartment()).getPathSpecifier();
    Company company = builder.join(department.getCompany()).getPathSpecifier();
    Select select = builder.select(employee);

    String query = select
        .where(employee.getStatus()).isNot(Status.DELETED)
        .and(department.getStatus()).isNot(Status.DELETED)
        .and(company.getStatus()).isNot(Status.DELETED)
        .orderBy(company.getName())
        .orderBy(department.getName())
        .orderBy(employee.getName())
        .getQueryString();

    String expected = "select a from test_Employee a " +
        "join a.department b " +
        "join b.company c " +
        "where a.status <> :a " +
        "and b.status <> :b " +
        "and c.status <> :c " +
        "order by c.name, b.name, a.name";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
          put("c", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void collectionJoins() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Company company = builder.from(Company.class);
    Department department = builder.join(company.getDepartments()).getPathSpecifier();
    Select select = builder.select(company);

    String query = select
        .where(company.getStatus()).isNot(Status.DELETED)
        .and(department.getStatus()).isNot(Status.DELETED)
        .orderBy(department.getName())
        .orderBy(company.getName())
        .getQueryString();

    String expected = "select a from test_Company a " +
        "join a.departments b " +
        "where a.status <> :a " +
        "and b.status <> :b " +
        "order by b.name, a.name";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void leftJoin() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Employee employee = builder.from(Employee.class);
    Department department = builder.leftJoin(employee.getDepartment()).getPathSpecifier();
    Company company = builder.leftJoin(department.getCompany()).getPathSpecifier();
    Select select = builder.select(employee);

    String query = select
        .where(employee.getStatus()).isNot(Status.DELETED)
        .and(department.getStatus()).isNot(Status.DELETED)
        .and(company.getStatus()).isNot(Status.DELETED)
        .orderBy(company.getName())
        .orderBy(department.getName())
        .orderBy(employee.getName())
        .getQueryString();

    String expected = "select a from test_Employee a " +
        "left join a.department b " +
        "left join b.company c " +
        "where a.status <> :a " +
        "and b.status <> :b " +
        "and c.status <> :c " +
        "order by c.name, b.name, a.name";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
          put("c", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void joinFetch() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Department department = builder.from(Department.class);
    builder.joinFetch(department.getCompany());
    builder.joinFetch(department.getEmployees());
    Select select = builder.select(department);

    String query = select
        .where(department.getStatus()).isNot(Status.DELETED)
        .orderBy(department.getName())
        .getQueryString();

    String expected = "select a from test_Department a " +
        "join fetch a.company " +
        "join fetch a.employees " +
        "where a.status <> :a " +
        "order by a.name";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void joinFetchWithAlias() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Department department = builder.from(Department.class);
    Company company = builder.joinFetchWithAlias(department.getCompany()).getPathSpecifier();
    Employee employee = builder.joinFetchWithAlias(department.getEmployees()).getPathSpecifier();
    Select select = builder.select(department);

    String query = select
        .where(department.getStatus()).isNot(Status.DELETED)
        .and(company.getStatus()).isNot(Status.DELETED)
        .orderBy(employee.getName())
        .getQueryString();

    String expected = "select a from test_Department a " +
        "join fetch a.company b " +
        "join fetch a.employees c " +
        "where a.status <> :a " +
        "and b.status <> :b " +
        "order by c.name";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void classJoin() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Employee employee = builder.from(Employee.class);
    Department department = builder.join(Department.class).getPathSpecifier();
    Select select = builder.select(employee);

    String query = select
        .where(employee.getStatus()).is(department.getStatus())
        .getQueryString();

    String expected = "select a from test_Employee a join test_Department b where a.status = b.status";

    assertEquals(expected, query);
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void classLeftJoin() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Employee employee = builder.from(Employee.class);
    Department department = builder.leftJoin(Department.class).getPathSpecifier();
    Select select = builder.select(employee);

    String query = select
        .where(employee.getStatus()).is(department.getStatus())
        .getQueryString();

    String expected = "select a from test_Employee a left join test_Department b where a.status = b.status";

    assertEquals(expected, query);
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void collectionJoinOn() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Department department = builder.from(Department.class);
    Employee employee = builder
        .join(department.getEmployees())
        .on(e -> $(e.getStatus()).isNot(Status.DELETED))
        .getPathSpecifier();
    Select select = builder.select(department);

    String query = select
        .where(department.getStatus()).isNot(Status.DELETED)
        .orderBy(employee.getName()).desc()
        .getQueryString();

    String expected = "select a from test_Department a " +
        "join a.employees b on b.status <> :a " +
        "where a.status <> :b " +
        "order by b.name desc";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void classJoinOn() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Employee employee = builder.from(Employee.class);
    Department department = builder
        .join(Department.class)
        .on(d -> $(d.getName()).is(employee.getName()))
        .getPathSpecifier();
    Select select = builder.select(employee);

    String query = select
        .where(employee.getStatus()).isNot(Status.DELETED)
        .and(department.getStatus()).isNot(Status.DELETED)
        .orderBy(department.getName()).desc()
        .getQueryString();

    String expected = "select a from test_Employee a " +
        "join test_Department b on b.name = a.name " +
        "where a.status <> :a " +
        "and b.status <> :b " +
        "order by b.name desc";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void joinNonEntity() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    builder.join(c.getStatus());
  }

  @Test
  public void joinSameThingMultipleTimes() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);

    Join<Department> join1 = builder.join(c.getDepartments());
    Join<Department> join2 = builder.join(c.getDepartments());

    assertSame(join1, join2);

    builder.join(c.getDepartments()).on(d -> $(d.getStatus()).isNot(Status.DELETED));

    assertEquals("select a from test_Company a join a.departments b on b.status <> :a", builder.select(c).getQueryString());
  }

  @Test
  public void joinAs() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Department d = builder.from(Department.class);
    Select select = builder.select(d);

    HeadOfDepartment h = builder
        .join(d.getEmployees())
        .as(HeadOfDepartment.class)
        .on(x -> $(x.isHeadOfDepartment()).is(Boolean.TRUE))
        .getPathSpecifier();

    String query = select
        .where(h.getStatus()).isNot(Status.DELETED)
        .getQueryString();

    String expected = "select a from test_Department a " +
        "join treat(a.employees as test_HeadOfDepartment) b on b.headOfDepartment = :a " +
        "where b.status <> :b";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Boolean.TRUE);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }
}
