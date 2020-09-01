package org.test.samples;

import org.junit.Test;
import org.test.JpqlBuilder;
import org.test.functions.Concat;
import org.test.model.Company;
import org.test.model.Department;
import org.test.model.Employee;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.test.functions.Functions.concat;
import static org.test.functions.Functions.lower;
import static org.test.functions.Functions.ltrim;
import static org.test.functions.Functions.rtrim;
import static org.test.functions.Functions.trim;
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

  @Test
  public void trimTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(trim(c.getName())).is(trim(c.getName(), '-'))
        .and(trim(lower(c.getName()))).is(trim(lower(c.getName()), '+'))
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where trim(a.name) = trim(:a from a.name) " +
        "and trim(lower(a.name)) = trim(:b from lower(a.name))";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", '-');
          put("b", '+');
        }},
        select.getParameters()
    );
  }

  @Test
  public void ltrimTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(ltrim(c.getName())).is(ltrim(c.getName(), '-'))
        .and(ltrim(lower(c.getName()))).is(ltrim(lower(c.getName()), '+'))
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where trim(leading from a.name) = trim(leading :a from a.name) " +
        "and trim(leading from lower(a.name)) = trim(leading :b from lower(a.name))";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", '-');
          put("b", '+');
        }},
        select.getParameters()
    );
  }

  @Test
  public void rtrimTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(rtrim(c.getName())).is(rtrim(c.getName(), '-'))
        .and(rtrim(lower(c.getName()))).is(rtrim(lower(c.getName()), '+'))
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where trim(trailing from a.name) = trim(trailing :a from a.name) " +
        "and trim(trailing from lower(a.name)) = trim(trailing :b from lower(a.name))";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", '-');
          put("b", '+');
        }},
        select.getParameters()
    );
  }

  @Test
  public void concatTest() {
    JpqlBuilder<Department> select = JpqlBuilder.select(Department.class);
    Department d = select.getPathSpecifier();

    Concat mixed = concat("%").concat(lower(d.getCompany().getName())).concat("%");

    String query = select
        .where(concat(d.getName(), " in Google")).is(concat("RnD in ", d.getCompany().getName()))
        .or(concat(lower(d.getName()), upper(d.getName()))).is("dummy")
        .or(lower(d.getName())).like(mixed)
        .getQueryString();

    String expected = "select a from test_Department a " +
        "where concat(a.name, :a) = concat(:b, a.company.name) " +
        "or concat(lower(a.name), upper(a.name)) = :c " +
        "or lower(a.name) like concat(:d, lower(a.company.name), :e)";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", " in Google");
          put("b", "RnD in ");
          put("c", "dummy");
          put("d", "%");
          put("e", "%");
        }},
        select.getParameters()
    );
  }
}
