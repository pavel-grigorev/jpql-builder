package org.test.samples;

import org.junit.Test;
import org.test.JpqlBuilder;
import org.test.Where;
import org.test.model.Employee;
import org.test.model.Status;
import org.test.operators.builders.ExpressionChain;

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
}
