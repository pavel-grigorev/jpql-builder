package org.test.jpqlbuildertests;

import org.junit.Test;
import org.test.JpqlBuilder;
import org.test.JpqlQuery;
import org.test.Where;
import org.test.model.Company;
import org.test.model.Department;
import org.test.model.Employee;
import org.test.model.Status;
import org.test.operators.builders.ExpressionChain;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.test.functions.Functions.lower;
import static org.test.functions.Functions.upper;
import static org.test.operators.builders.OperatorBuilder.$;
import static org.test.operators.builders.OperatorBuilder.not;
import static org.test.operators.builders.StringOperatorBuilder.$;

public class JpqlBuilderTest {
  @Test
  public void testMinimal() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    assertEquals("select a from test_Employee a", select.getQueryString());
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testOrderBy() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    assertEquals(
        "select a from test_Employee a order by lower(a.name) desc, a.id",
        select.orderBy(lower(employee.getName())).desc().orderBy(employee.getId()).getQueryString()
    );
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testOrderByWithNullsOrdering() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    assertEquals(
        "select a from test_Employee a order by upper(a.name) desc nulls first, a.id asc nulls last, a.department",
        select
            .orderBy(upper(employee.getName())).desc().nullsFirst()
            .orderBy(employee.getId()).asc().nullsLast()
            .orderBy(employee.getDepartment())
            .getQueryString()
    );
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testWhereAndOrderBy() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    assertEquals(
        "select a from test_Employee a where a.status <> :a order by a.name desc",
        select
            .where(employee.getStatus()).isNot(Status.DELETED)
            .orderBy(employee.getName()).desc().getQueryString()
    );
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testWhereString() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    assertEquals(
        "select a from test_Employee a where a.name like :a",
        select.where(employee.getName()).like("%test%").getQueryString()
    );
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "%test%");
        }},
        select.getParameters()
    );
  }

  @Test
  public void testWhereExpression() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    String query = select
        .where(
            $(employee.getName()).is("Test")
                .or(employee.getStatus()).is(Status.ACTIVE)
        )
        .and(employee.getId()).isNot(1L)
        .getQueryString();
    assertEquals(
        "select a from test_Employee a " +
            "where (a.name = :a or a.status = :b) " +
            "and a.id <> :c",
        query
    );
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
  public void testWhere() {
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
                    $(employee.getStatus()).is(Status.ACTIVE)
                        .and(employee.getName()).like("B")
                ))
                .and(employee.getId()).between(10L, 20L)
                .and(
                    $(employee.getStatus()).in(Status.ACTIVE, Status.SUSPENDED)
                    .or(employee.getStatus()).notIn(Status.DELETED, Status.DISABLED)
                )
        )
        .or(
            $(employee.getId()).greaterThanOrEqual(0L)
            .and(employee.getId()).lessThanOrEqual(100L)
        )
        .getQueryString();
    assertEquals(
        "select a from test_Employee a " +
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
            "or (a.id >= :o and a.id <= :p)",
        query
    );
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

  @Test
  public void testEntityJoins() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select.join(employee.getDepartment()).getPathSpecifier();
    Company company = select.join(department.getCompany()).getPathSpecifier();
    assertEquals(
        "select a from test_Employee a " +
            "join a.department b " +
            "join b.company c " +
            "where a.status <> :a " +
            "and b.status <> :b " +
            "and c.status <> :c " +
            "order by c.name, b.name, a.name",
        select
            .where(employee.getStatus()).isNot(Status.DELETED)
            .and(department.getStatus()).isNot(Status.DELETED)
            .and(company.getStatus()).isNot(Status.DELETED)
            .orderBy(company.getName())
            .orderBy(department.getName())
            .orderBy(employee.getName())
            .getQueryString()
    );
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
  public void testCollectionJoins() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company company = select.getPathSpecifier();
    Department department = select.join(company.getDepartments()).getPathSpecifier();
    assertEquals(
        "select a from test_Company a " +
            "join a.departments b " +
            "where a.status <> :a " +
            "and b.status <> :b " +
            "order by b.name, a.name",
        select
            .where(company.getStatus()).isNot(Status.DELETED)
            .and(department.getStatus()).isNot(Status.DELETED)
            .orderBy(department.getName())
            .orderBy(company.getName())
            .getQueryString()
    );
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testLeftJoin() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select.leftJoin(employee.getDepartment()).getPathSpecifier();
    Company company = select.leftJoin(department.getCompany()).getPathSpecifier();
    assertEquals(
        "select a from test_Employee a " +
            "left join a.department b " +
            "left join b.company c " +
            "where a.status <> :a " +
            "and b.status <> :b " +
            "and c.status <> :c " +
            "order by c.name, b.name, a.name",
        select
            .where(employee.getStatus()).isNot(Status.DELETED)
            .and(department.getStatus()).isNot(Status.DELETED)
            .and(company.getStatus()).isNot(Status.DELETED)
            .orderBy(company.getName())
            .orderBy(department.getName())
            .orderBy(employee.getName())
            .getQueryString()
    );
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
  public void testJoinFetch() {
    JpqlBuilder<Department> select = JpqlBuilder.select(Department.class);
    Department department = select.getPathSpecifier();
    select.joinFetch(department.getCompany());
    select.joinFetch(department.getEmployees());
    assertEquals(
        "select a from test_Department a " +
            "join fetch a.company " +
            "join fetch a.employees " +
            "where a.status <> :a " +
            "order by a.name",
        select
            .where(department.getStatus()).isNot(Status.DELETED)
            .orderBy(department.getName())
            .getQueryString()
    );
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testJoinFetchWithAlias() {
    JpqlBuilder<Department> select = JpqlBuilder.select(Department.class);
    Department department = select.getPathSpecifier();
    Company company = select.joinFetchWithAlias(department.getCompany()).getPathSpecifier();
    Employee employee = select.joinFetchWithAlias(department.getEmployees()).getPathSpecifier();
    assertEquals(
        "select a from test_Department a " +
            "join fetch a.company b " +
            "join fetch a.employees c " +
            "where a.status <> :a " +
            "and b.status <> :b " +
            "order by c.name",
        select
            .where(department.getStatus()).isNot(Status.DELETED)
            .and(company.getStatus()).isNot(Status.DELETED)
            .orderBy(employee.getName())
            .getQueryString()
    );
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testClassJoin() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select.join(Department.class).getPathSpecifier();
    assertEquals(
        "select a from test_Employee a " +
            "join test_Department b " +
            "where a.status = b.status",
        select
            .where(employee.getStatus()).is(department.getStatus())
            .getQueryString()
    );
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testClassLeftJoin() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select.leftJoin(Department.class).getPathSpecifier();
    assertEquals(
        "select a from test_Employee a " +
            "left join test_Department b " +
            "where a.status = b.status",
        select
            .where(employee.getStatus()).is(department.getStatus())
            .getQueryString()
    );
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testCollectionJoinOn() {
    JpqlBuilder<Department> select = JpqlBuilder.select(Department.class);
    Department department = select.getPathSpecifier();
    Employee employee = select
        .join(department.getEmployees())
        .on(e -> $(e.getStatus()).isNot(Status.DELETED))
        .getPathSpecifier();
    assertEquals(
        "select a from test_Department a " +
            "join a.employees b on b.status <> :a " +
            "where a.status <> :b " +
            "order by b.name desc",
        select
            .where(department.getStatus()).isNot(Status.DELETED)
            .orderBy(employee.getName()).desc()
            .getQueryString()
    );
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testClassJoinOn() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select
        .join(Department.class)
        .on(d -> $(d.getName()).is(employee.getName()))
        .getPathSpecifier();
    assertEquals(
        "select a from test_Employee a " +
            "join test_Department b on b.name = a.name " +
            "where a.status <> :a " +
            "and b.status <> :b " +
            "order by b.name desc",
        select
            .where(employee.getStatus()).isNot(Status.DELETED)
            .and(department.getStatus()).isNot(Status.DELETED)
            .orderBy(department.getName()).desc()
            .getQueryString()
    );
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testLowerUpper() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select
        .join(Department.class)
        .on(d -> $(lower(d.getName())).like(lower(employee.getName())))
        .getPathSpecifier();
    assertEquals(
        "select a from test_Employee a " +
            "join test_Department b on lower(b.name) like lower(a.name) " +
            "where upper(a.name) like upper(:a) " +
            "or lower(a.name) like lower(b.name) " +
            "and upper(a.name) not like upper(b.name)",
        select
            .where(upper(employee.getName())).like(upper("%test%"))
            .or(lower(employee.getName())).like(lower(department.getName()))
            .and(upper(employee.getName())).notLike(upper(department.getName()))
            .getQueryString()
    );
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "%test%");
        }},
        select.getParameters()
    );
  }

  @Test
  public void testDynamicQuery() {
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

    assertEquals(
        "select a from test_Employee a " +
            "where a.status <> :a " +
            "and a.name like :b " +
            "and (a.id = :c or a.id = :d or a.id = :e) " +
            "order by a.id asc, a.name desc",
        select.getQueryString()
    );
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
  public void oneLinerOrderBy() {
    JpqlQuery query = JpqlBuilder.select(Company.class).orderBy(Company::getName).desc().orderBy(Company::getId);
    assertEquals(
        "select a from test_Company a order by a.name desc, a.id",
        query.getQueryString()
    );
    assertEquals(new HashMap<String, Object>(), query.getParameters());
  }

  @Test
  public void oneLinerWhere() {
    JpqlQuery query = JpqlBuilder.select(Company.class).where(c -> $(c.getStatus()).isNot(Status.DELETED));
    assertEquals(
        "select a from test_Company a where a.status <> :a",
        query.getQueryString()
    );
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        query.getParameters()
    );
  }

  @Test
  public void oneLinerWhereAndOrderBy() {
    JpqlQuery query = JpqlBuilder
        .select(Company.class)
        .where(c -> $(c.getStatus()).isNot(Status.DELETED).and(c.getName()).like("%test%"))
        .orderBy(Company::getName).desc();
    assertEquals(
        "select a from test_Company a where a.status <> :a and a.name like :b order by a.name desc",
        query.getQueryString()
    );
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", "%test%");
        }},
        query.getParameters()
    );
  }
}
