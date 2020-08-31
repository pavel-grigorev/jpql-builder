package org.test.samples;

import org.junit.Test;
import org.test.JpqlBuilder;
import org.test.Where;
import org.test.model.Company;
import org.test.model.Employee;
import org.test.model.Status;
import org.test.operators.builders.ExpressionChain;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.test.operators.builders.OperatorBuilder.$;

public class DynamicQueryTest {
  @Test
  public void dynamicQuery() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();

    Where<Employee> where = select.where(employee.getStatus()).isNot(Status.DELETED);
    where.and(employee.getName()).like("%test%");

    ExpressionChain idFilter = $(employee.getId()).is(1L);
    idFilter.or(employee.getId()).is(2L);
    idFilter.or(employee.getId()).is(3L);

    where.and(idFilter);

    where.orderBy(employee.getId()).asc();
    select.orderBy(employee.getName()).desc();

    String expected = "select a from test_Employee a " +
        "where a.status <> :a " +
        "and a.name like :b " +
        "and (a.id = :c or a.id = :d or a.id = :e) " +
        "order by a.id asc, a.name desc";

    assertEquals(expected, select.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", "%test%");
          put("c", 1L);
          put("d", 2L);
          put("e", 3L);
        }},
        select.getParameters()
    );
  }

  @Test
  public void dynamicQuery1() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();
    Where<Company> where = null;

    for (String name : Arrays.asList("Google", "Apple")) {
      if (where == null) {
        where = select.where(c.getName()).is(name);
      } else {
        where.or(c.getName()).is(name);
      }
    }

    select.join(c.getDepartments()).on(d -> $(d.getStatus()).isNot(Status.DELETED));

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
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();
    ExpressionChain condition = null;

    for (String name : Arrays.asList("Google", "Apple")) {
      if (condition == null) {
        condition = $(c.getName()).is(name);
      } else {
        condition.or(c.getName()).is(name);
      }
    }

    select.join(c.getDepartments()).on(d -> $(d.getStatus()).isNot(Status.DELETED));
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
