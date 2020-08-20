package org.test.samples;

import org.junit.Test;
import org.test.JpqlBuilder;
import org.test.model.Employee;
import org.test.model.Status;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.test.functions.Functions.lower;
import static org.test.functions.Functions.upper;

public class OrderByTest {
  @Test
  public void orderBy() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();

    String query = select
        .orderBy(lower(employee.getName())).desc()
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
        .orderBy(upper(employee.getName())).desc().nullsFirst()
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
