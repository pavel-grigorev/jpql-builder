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
import org.thepavel.jpqlbuilder.Select;
import org.thepavel.jpqlbuilder.functions.Cast;
import org.thepavel.jpqlbuilder.functions.Concat;
import org.thepavel.jpqlbuilder.functions.Extract;
import org.thepavel.jpqlbuilder.functions.JpqlFunction;
import org.thepavel.jpqlbuilder.model.Company;
import org.thepavel.jpqlbuilder.model.Department;
import org.thepavel.jpqlbuilder.model.Employee;
import org.thepavel.jpqlbuilder.model.Status;
import org.thepavel.jpqlbuilder.JpqlBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.functions.Functions._case;
import static org.thepavel.jpqlbuilder.functions.Functions.abs;
import static org.thepavel.jpqlbuilder.functions.Functions.add;
import static org.thepavel.jpqlbuilder.functions.Functions.cast;
import static org.thepavel.jpqlbuilder.functions.Functions.coalesce;
import static org.thepavel.jpqlbuilder.functions.Functions.column;
import static org.thepavel.jpqlbuilder.functions.Functions.concat;
import static org.thepavel.jpqlbuilder.functions.Functions.currentDate;
import static org.thepavel.jpqlbuilder.functions.Functions.currentTime;
import static org.thepavel.jpqlbuilder.functions.Functions.currentTimestamp;
import static org.thepavel.jpqlbuilder.functions.Functions.div;
import static org.thepavel.jpqlbuilder.functions.Functions.extract;
import static org.thepavel.jpqlbuilder.functions.Functions.func;
import static org.thepavel.jpqlbuilder.functions.Functions.function;
import static org.thepavel.jpqlbuilder.functions.Functions.index;
import static org.thepavel.jpqlbuilder.functions.Functions.key;
import static org.thepavel.jpqlbuilder.functions.Functions.leftTrim;
import static org.thepavel.jpqlbuilder.functions.Functions.length;
import static org.thepavel.jpqlbuilder.functions.Functions.locate;
import static org.thepavel.jpqlbuilder.functions.Functions.lower;
import static org.thepavel.jpqlbuilder.functions.Functions.mod;
import static org.thepavel.jpqlbuilder.functions.Functions.multi;
import static org.thepavel.jpqlbuilder.functions.Functions.nullif;
import static org.thepavel.jpqlbuilder.functions.Functions.regexp;
import static org.thepavel.jpqlbuilder.functions.Functions.rightTrim;
import static org.thepavel.jpqlbuilder.functions.Functions.size;
import static org.thepavel.jpqlbuilder.functions.Functions.sql;
import static org.thepavel.jpqlbuilder.functions.Functions.sqrt;
import static org.thepavel.jpqlbuilder.functions.Functions.sub;
import static org.thepavel.jpqlbuilder.functions.Functions.substring;
import static org.thepavel.jpqlbuilder.functions.Functions.trim;
import static org.thepavel.jpqlbuilder.functions.Functions.type;
import static org.thepavel.jpqlbuilder.functions.Functions.upper;
import static org.thepavel.jpqlbuilder.functions.Functions.value;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class FunctionsTest {
  @Test
  public void lowerUpper() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Employee employee = builder.from(Employee.class);

    Select select = builder.select(employee);
    Department department = builder
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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Department d = builder.from(Department.class);
    Select select = builder.select(d);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Employee e = builder.from(Employee.class);
    Select select = builder.select(e);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Employee e = builder.from(Employee.class);
    Select select = builder.select(e);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Employee e = builder.from(Employee.class);
    Select select = builder.select(e);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Employee e = builder.from(Employee.class);
    Select select = builder.select(e);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Employee e = builder.from(Employee.class);
    Select select = builder.select(e);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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

  @Test
  public void indexTest() {
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

    builder.join(c.getDepartments()).on(d -> $(index(d)).between(1, 10)).getPathSpecifier();

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

    builder.join(c.getHeads()).on(h -> $(key(h)).between(1L, 10L));

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

    builder.join(c.getHeads()).on(h -> $(value(h).getStatus()).isNot(Status.DELETED));

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

    String query = select
        .where(size(c.getDepartments())).greaterThan(5)
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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

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
    JpqlBuilder builder = JpqlBuilder.builder();
    Department d = builder.from(Department.class);
    Select select = builder.select(d);

    String query = select
        .where(type(d)).is(Department.class)
        .and(type(d.getCompany())).is(Company.class)
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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

    List<String> arguments = Arrays.asList(c.getName(), "dummy");
    String query = select
        .where(function("coalesce", arguments)).is("dummy")
        .and(function("coalesce", cast(c.getStatus(), Cast.Type.STRING), "active")).is("active")
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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

    List<String> arguments = Arrays.asList(c.getName(), "dummy");
    String query = select
        .where(func("coalesce", arguments)).is("dummy")
        .and(func("coalesce", cast(c.getStatus(), Cast.Type.STRING), "active")).is("active")
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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

    String query = select
        .where(sql("cast(? as varchar)", c.getStatus())).is("active")
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
    JpqlBuilder builder = JpqlBuilder.builder();
    Company c = builder.from(Company.class);
    Select select = builder.select(c);

    String query = select
        .where(column("rowid", c)).lessThan(100)
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
