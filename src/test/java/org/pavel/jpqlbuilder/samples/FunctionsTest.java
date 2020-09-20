package org.pavel.jpqlbuilder.samples;

import org.junit.Test;
import org.pavel.jpqlbuilder.functions.Cast;
import org.pavel.jpqlbuilder.functions.Concat;
import org.pavel.jpqlbuilder.functions.Extract;
import org.pavel.jpqlbuilder.functions.Functions;
import org.pavel.jpqlbuilder.functions.JpqlFunction;
import org.pavel.jpqlbuilder.model.Company;
import org.pavel.jpqlbuilder.model.Department;
import org.pavel.jpqlbuilder.model.Employee;
import org.pavel.jpqlbuilder.model.Status;
import org.pavel.jpqlbuilder.JpqlBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.pavel.jpqlbuilder.functions.Functions._case;
import static org.pavel.jpqlbuilder.functions.Functions.abs;
import static org.pavel.jpqlbuilder.functions.Functions.add;
import static org.pavel.jpqlbuilder.functions.Functions.coalesce;
import static org.pavel.jpqlbuilder.functions.Functions.concat;
import static org.pavel.jpqlbuilder.functions.Functions.div;
import static org.pavel.jpqlbuilder.functions.Functions.extract;
import static org.pavel.jpqlbuilder.functions.Functions.func;
import static org.pavel.jpqlbuilder.functions.Functions.function;
import static org.pavel.jpqlbuilder.functions.Functions.leftTrim;
import static org.pavel.jpqlbuilder.functions.Functions.length;
import static org.pavel.jpqlbuilder.functions.Functions.locate;
import static org.pavel.jpqlbuilder.functions.Functions.lower;
import static org.pavel.jpqlbuilder.functions.Functions.multi;
import static org.pavel.jpqlbuilder.functions.Functions.nullif;
import static org.pavel.jpqlbuilder.functions.Functions.regexp;
import static org.pavel.jpqlbuilder.functions.Functions.rightTrim;
import static org.pavel.jpqlbuilder.functions.Functions.sql;
import static org.pavel.jpqlbuilder.functions.Functions.sqrt;
import static org.pavel.jpqlbuilder.functions.Functions.sub;
import static org.pavel.jpqlbuilder.functions.Functions.substring;
import static org.pavel.jpqlbuilder.functions.Functions.trim;
import static org.pavel.jpqlbuilder.functions.Functions.upper;
import static org.pavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class FunctionsTest {
  @Test
  public void lowerUpper() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select
        .join(Department.class)
        .on(d -> $(lower(d.getName())).like(Functions.lower(employee.getName())))
        .getPathSpecifier();

    String query = select
        .where(Functions.upper(employee.getName())).like(Functions.upper("%test%"))
        .or(Functions.lower(employee.getName())).like(Functions.lower(department.getName()))
        .and(Functions.upper(Functions.lower(employee.getName()))).notLike(Functions.upper(Functions.lower(department.getName())))
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
        .where(Functions.trim(c.getName())).is(Functions.trim(c.getName(), '-'))
        .and(Functions.trim(Functions.lower(c.getName()))).is(Functions.trim(Functions.lower(c.getName()), '+'))
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
        .where(Functions.leftTrim(c.getName())).is(Functions.leftTrim(c.getName(), '-'))
        .and(Functions.leftTrim(Functions.lower(c.getName()))).is(Functions.leftTrim(Functions.lower(c.getName()), '+'))
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
        .where(Functions.rightTrim(c.getName())).is(Functions.rightTrim(c.getName(), '-'))
        .and(Functions.rightTrim(Functions.lower(c.getName()))).is(Functions.rightTrim(Functions.lower(c.getName()), '+'))
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

    Concat mixed = Functions.concat("%").concat(Functions.lower(d.getCompany().getName())).concat("%");

    String query = select
        .where(Functions.concat(d.getName(), " in Google")).is(Functions.concat("RnD in ", d.getCompany().getName()))
        .or(Functions.concat(Functions.lower(d.getName()), Functions.upper(d.getName()))).is("dummy")
        .or(Functions.lower(d.getName())).like(mixed)
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
        .where(Functions.substring(c.getName(), 1, 2)).is("Go")
        .or(Functions.substring(Functions.lower(c.getName()), 3)).is("ogle")
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
        .where(Functions.length(c.getName())).is(6)
        .or(Functions.length(Functions.concat(c.getName(), "dummy"))).is(10)
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
        .where(Functions.locate("dummy", c.getName())).is(0)
        .or(Functions.locate(Functions.lower("test"), Functions.lower(c.getName()), 1)).is(10)
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
        .where(Functions.add(Functions.length(c.getName()), 10)).is(15)
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
        .where(Functions.sub(Functions.length(c.getName()), 10)).is(15)
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
        .where(Functions.multi(Functions.length(c.getName()), 10)).is(15)
        .and(Functions.multi(Functions.add(1, 2), Functions.multi(3, 4))).is(Functions.multi(Functions.sub(1, 2), Functions.div(3, 4)))
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
        .where(Functions.div(Functions.length(c.getName()), 10)).is(15)
        .and(Functions.multi(Functions.add(1, 2), Functions.sub(3, 4))).is(Functions.div(Functions.add(1, 2), Functions.sub(3, 4)))
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
        .where(Functions.abs(Functions.sub(Functions.length(c.getName()), 10))).is(1)
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
        .where(Functions.mod(Functions.div(Functions.length(c.getName()), 10))).is(1)
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
        .where(Functions.sqrt(Functions.add(Functions.length(c.getName()), 10))).is(10)
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
        .where(e.getEmploymentDate()).is(Functions.currentDate())
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
        .where(e.getEmploymentDate()).is(Functions.currentTime())
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
        .where(e.getEmploymentDate()).is(Functions.currentTimestamp())
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

    JpqlFunction<Integer> code = Functions._case(Functions.substring(e.getName(), 1, 2))
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

    JpqlFunction<Integer> code = Functions._case()
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
        .where(Functions.length(Functions.coalesce(c.getName(), "dummy"))).greaterThan(4)
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
        .where(Functions.nullif(Functions.lower(c.getName()), "google")).isNull()
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
        .where(Functions.cast(c.getStatus(), Cast.Type.STRING)).is("active")
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
        .where(Functions.extract(e.getEmploymentDate(), Extract.Part.YEAR)).is(Functions.extract(Functions.currentDate(), Extract.Part.YEAR))
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
        .where(Functions.regexp(c.getName(), "^Go*")).is(Boolean.TRUE)
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

  @Test
  public void indexTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    select.join(c.getDepartments()).on(d -> $(Functions.index(d)).between(1, 10)).getPathSpecifier();

    String expected = "select a from test_Company a " +
        "join a.departments b on index(b) between :a and :b";

    assertEquals(expected, select.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 1);
          put("b", 10);
        }},
        select.getParameters()
    );
  }

  @Test
  public void keyTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    select.join(c.getHeads()).on(h -> $(Functions.key(h)).between(1L, 10L));

    String expected = "select a from test_Company a " +
        "join a.heads b on key(b) between :a and :b";

    assertEquals(expected, select.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 1L);
          put("b", 10L);
        }},
        select.getParameters()
    );
  }

  @Test
  public void valueTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    select.join(c.getHeads()).on(h -> $(Functions.value(h).getStatus()).isNot(Status.DELETED));

    String expected = "select a from test_Company a " +
        "join a.heads b on value(b).status <> :a";

    assertEquals(expected, select.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void sizeTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(Functions.size(c.getDepartments())).greaterThan(5)
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where size(a.departments) > :a";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 5);
        }},
        select.getParameters()
    );
  }

  @Test
  public void isEmptyTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(c.getDepartments()).isEmpty()
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where a.departments is empty";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>(),
        select.getParameters()
    );
  }

  @Test
  public void isNotEmptyTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(c.getDepartments()).isNotEmpty()
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where a.departments is not empty";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>(),
        select.getParameters()
    );
  }

  @Test
  public void memberOfTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();
    Department d = new Department();

    String query = select
        .where(d).memberOf(c.getDepartments())
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where :a member of a.departments";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", d);
        }},
        select.getParameters()
    );
  }

  @Test
  public void notMemberOfTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();
    Department d = new Department();

    String query = select
        .where(d).notMemberOf(c.getDepartments())
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where :a not member of a.departments";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", d);
        }},
        select.getParameters()
    );
  }

  @Test
  public void typeTest() {
    JpqlBuilder<Department> select = JpqlBuilder.select(Department.class);
    Department d = select.getPathSpecifier();

    String query = select
        .where(Functions.type(d)).is(Department.class)
        .and(Functions.type(d.getCompany())).is(Company.class)
        .getQueryString();

    String expected = "select a from test_Department a " +
        "where type(a) = test_Department " +
        "and type(a.company) = test_Company";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>(),
        select.getParameters()
    );
  }

  @Test
  public void functionTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    List<String> arguments = Arrays.asList(c.getName(), "dummy");
    String query = select
        .where(Functions.function("coalesce", arguments)).is("dummy")
        .and(Functions.function("coalesce", Functions.cast(c.getStatus(), Cast.Type.STRING), "active")).is("active")
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where function('coalesce', a.name, :a) = :b " +
        "and function('coalesce', cast(a.status string), :c) = :d";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "dummy");
          put("b", "dummy");
          put("c", "active");
          put("d", "active");
        }},
        select.getParameters()
    );
  }

  @Test
  public void funcTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    List<String> arguments = Arrays.asList(c.getName(), "dummy");
    String query = select
        .where(Functions.func("coalesce", arguments)).is("dummy")
        .and(Functions.func("coalesce", Functions.cast(c.getStatus(), Cast.Type.STRING), "active")).is("active")
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where func('coalesce', a.name, :a) = :b " +
        "and func('coalesce', cast(a.status string), :c) = :d";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "dummy");
          put("b", "dummy");
          put("c", "active");
          put("d", "active");
        }},
        select.getParameters()
    );
  }

  @Test
  public void sqlTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(Functions.sql("cast(? as varchar)", c.getStatus())).is("active")
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where sql('cast(? as varchar)', a.status) = :a";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "active");
        }},
        select.getParameters()
    );
  }

  @Test
  public void columnTest() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    String query = select
        .where(Functions.column("rowid", c)).lessThan(100)
        .getQueryString();

    String expected = "select a from test_Company a " +
        "where column('rowid', a) < :a";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 100);
        }},
        select.getParameters()
    );
  }
}
