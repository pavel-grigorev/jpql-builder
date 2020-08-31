package org.test.samples;

import org.junit.Test;
import org.test.JpqlBuilder;
import org.test.model.Department;
import org.test.model.Employee;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.test.functions.Functions.lower;
import static org.test.functions.Functions.upper;
import static org.test.operators.builders.OperatorBuilder.$;

public class FunctionsTest {
  @Test
  public void lowerUpper() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select
        .join(Department.class)
        .on(d -> $(lower(d.getName())).like(lower(employee.getName())))
        .getPathSpecifier();

    String query = select
        .where(upper(employee.getName())).like(upper("%test%"))
        .or(lower(employee.getName())).like(lower(department.getName()))
        .and(upper(lower(employee.getName()))).notLike(upper(lower(department.getName())))
        .getQueryString();

    String expected = "select a from test_Employee a " +
        "join test_Department b on lower(b.name) like lower(a.name) " +
        "where upper(a.name) like upper(:a) " +
        "or lower(a.name) like lower(b.name) " +
        "and upper(lower(a.name)) not like upper(lower(b.name))";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "%test%");
        }},
        select.getParameters()
    );
  }
}
