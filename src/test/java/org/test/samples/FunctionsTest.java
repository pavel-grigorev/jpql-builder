package org.test.samples;

import org.junit.Test;
import org.test.JpqlBuilder;
import org.test.functions.Cast;
import org.test.functions.Concat;
import org.test.functions.Extract;
import org.test.functions.JpqlFunction;
import org.test.model.Company;
import org.test.model.Department;
import org.test.model.Employee;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.test.functions.Functions._case;
import static org.test.functions.Functions.abs;
import static org.test.functions.Functions.add;
import static org.test.functions.Functions.cast;
import static org.test.functions.Functions.coalesce;
import static org.test.functions.Functions.concat;
import static org.test.functions.Functions.currentDate;
import static org.test.functions.Functions.currentTime;
import static org.test.functions.Functions.currentTimestamp;
import static org.test.functions.Functions.div;
import static org.test.functions.Functions.extract;
import static org.test.functions.Functions.leftTrim;
import static org.test.functions.Functions.length;
import static org.test.functions.Functions.locate;
import static org.test.functions.Functions.lower;
import static org.test.functions.Functions.mod;
import static org.test.functions.Functions.multi;
import static org.test.functions.Functions.nullif;
import static org.test.functions.Functions.regexp;
import static org.test.functions.Functions.rightTrim;
import static org.test.functions.Functions.sqrt;
import static org.test.functions.Functions.sub;
import static org.test.functions.Functions.substring;
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
        .where(leftTrim(c.getName())).is(leftTrim(c.getName(), '-'))
        .and(leftTrim(lower(c.getName()))).is(leftTrim(lower(c.getName()), '+'))
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
        .where(rightTrim(c.getName())).is(rightTrim(c.getName(), '-'))
        .and(rightTrim(lower(c.getName()))).is(rightTrim(lower(c.getName()), '+'))
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

  @Test
  public void substringTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(substring(c.getName(), 1, 2)).is("Go")
        .or(substring(lower(c.getName()), 3)).is("ogle")
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where substring(a.name, :a, :b) = :c " +
        "or substring(lower(a.name), :d) = :e";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 1);
          put("b", 2);
          put("c", "Go");
          put("d", 3);
          put("e", "ogle");
        }},
        select.getParameters()
    );
  }

  @Test
  public void lengthTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(length(c.getName())).is(6)
        .or(length(concat(c.getName(), "dummy"))).is(10)
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where length(a.name) = :a " +
        "or length(concat(a.name, :b)) = :c";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 6);
          put("b", "dummy");
          put("c", 10);
        }},
        select.getParameters()
    );
  }

  @Test
  public void locateTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(locate("dummy", c.getName())).is(0)
        .or(locate(lower("test"), lower(c.getName()), 1)).is(10)
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where locate(:a, a.name) = :b " +
        "or locate(lower(:c), lower(a.name), :d) = :e";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "dummy");
          put("b", 0);
          put("c", "test");
          put("d", 1);
          put("e", 10);
        }},
        select.getParameters()
    );
  }

  @Test
  public void addTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(add(length(c.getName()), 10)).is(15)
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where length(a.name) + :a = :b";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 10);
          put("b", 15);
        }},
        select.getParameters()
    );
  }

  @Test
  public void subTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(sub(length(c.getName()), 10)).is(15)
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where length(a.name) - :a = :b";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 10);
          put("b", 15);
        }},
        select.getParameters()
    );
  }

  @Test
  public void multiTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(multi(length(c.getName()), 10)).is(15)
        .and(multi(add(1, 2), multi(3, 4))).is(multi(sub(1, 2), div(3, 4)))
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where length(a.name) * :a = :b " +
        "and (:c + :d) * :e * :f = (:g - :h) * (:i / :j)";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 10);
          put("b", 15);
          put("c", 1);
          put("d", 2);
          put("e", 3);
          put("f", 4);
          put("g", 1);
          put("h", 2);
          put("i", 3);
          put("j", 4);
        }},
        select.getParameters()
    );
  }

  @Test
  public void divTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(div(length(c.getName()), 10)).is(15)
        .and(multi(add(1, 2), sub(3, 4))).is(div(add(1, 2), sub(3, 4)))
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where length(a.name) / :a = :b " +
        "and (:c + :d) * (:e - :f) = (:g + :h) / (:i - :j)";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 10);
          put("b", 15);
          put("c", 1);
          put("d", 2);
          put("e", 3);
          put("f", 4);
          put("g", 1);
          put("h", 2);
          put("i", 3);
          put("j", 4);
        }},
        select.getParameters()
    );
  }

  @Test
  public void absTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(abs(sub(length(c.getName()), 10))).is(1)
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where abs(length(a.name) - :a) = :b";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 10);
          put("b", 1);
        }},
        select.getParameters()
    );
  }

  @Test
  public void modTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(mod(div(length(c.getName()), 10))).is(1)
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where mod(length(a.name) / :a) = :b";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 10);
          put("b", 1);
        }},
        select.getParameters()
    );
  }

  @Test
  public void sqrtTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(sqrt(add(length(c.getName()), 10))).is(10)
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where sqrt(length(a.name) + :a) = :b";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 10);
          put("b", 10);
        }},
        select.getParameters()
    );
  }

  @Test
  public void currentDateTest() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee e = select.getPathSpecifier();

    String query = select
        .where(e.getEmploymentDate()).is(currentDate())
        .getQueryString();

    String expected = "select a from test_Employee a " +
        "where a.employmentDate = current_date";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>(),
        select.getParameters()
    );
  }

  @Test
  public void currentTimeTest() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee e = select.getPathSpecifier();

    String query = select
        .where(e.getEmploymentDate()).is(currentTime())
        .getQueryString();

    String expected = "select a from test_Employee a " +
        "where a.employmentDate = current_time";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>(),
        select.getParameters()
    );
  }

  @Test
  public void currentTimestampTest() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee e = select.getPathSpecifier();

    String query = select
        .where(e.getEmploymentDate()).is(currentTimestamp())
        .getQueryString();

    String expected = "select a from test_Employee a " +
        "where a.employmentDate = current_timestamp";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>(),
        select.getParameters()
    );
  }

  @Test
  public void caseTest() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee e = select.getPathSpecifier();

    JpqlFunction<Integer> code = _case(substring(e.getName(), 1, 2))
        .when("Mr").then(1)
        .when("Ms").then(2)
        .orElse(0);

    String query = select
        .where(code).is(0)
        .getQueryString();

    String expected = "select a from test_Employee a " +
        "where case substring(a.name, :a, :b) when :c then :d when :e then :f else :g end = :h";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 1);
          put("b", 2);
          put("c", "Mr");
          put("d", 1);
          put("e", "Ms");
          put("f", 2);
          put("g", 0);
          put("h", 0);
        }},
        select.getParameters()
    );
  }

  @Test
  public void casePredicateTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    JpqlFunction<Integer> code = _case()
        .when(c.getName()).is("Google").then(1)
        .when(c.getName()).is("Apple").then(2)
        .orElse(0);

    String query = select
        .where(code).is(0)
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where case when a.name = :a then :b when a.name = :c then :d else :e end = :f";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "Google");
          put("b", 1);
          put("c", "Apple");
          put("d", 2);
          put("e", 0);
          put("f", 0);
        }},
        select.getParameters()
    );
  }

  @Test
  public void coalesceTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(length(coalesce(c.getName(), "dummy"))).greaterThan(4)
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where length(coalesce(a.name, :a)) > :b";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "dummy");
          put("b", 4);
        }},
        select.getParameters()
    );
  }

  @Test
  public void nullifTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(nullif(lower(c.getName()), "google")).isNull()
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where nullif(lower(a.name), :a) is null";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "google");
        }},
        select.getParameters()
    );
  }

  @Test
  public void castTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(cast(c.getStatus(), Cast.Type.STRING)).is("active")
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where cast(a.status string) = :a";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "active");
        }},
        select.getParameters()
    );
  }

  @Test
  public void extractTest() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee e = select.getPathSpecifier();

    String query = select
        .where(extract(e.getEmploymentDate(), Extract.Part.YEAR)).is(extract(currentDate(), Extract.Part.YEAR))
        .getQueryString();

    String expected = "select a from test_Employee a " +
        "where extract(YEAR from a.employmentDate) = extract(YEAR from current_date)";

    assertEquals(expected, query);
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void regexpTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(regexp(c.getName(), "^Go*")).is(Boolean.TRUE)
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where a.name regexp :a = :b";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "^Go*");
          put("b", Boolean.TRUE);
        }},
        select.getParameters()
    );
  }
}
