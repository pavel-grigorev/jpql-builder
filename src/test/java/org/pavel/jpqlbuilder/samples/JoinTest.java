package org.pavel.jpqlbuilder.samples;

import org.junit.Test;
import org.pavel.jpqlbuilder.Join;
import org.pavel.jpqlbuilder.model.Department;
import org.pavel.jpqlbuilder.model.Employee;
import org.pavel.jpqlbuilder.model.HeadOfDepartment;
import org.pavel.jpqlbuilder.model.Status;
import org.pavel.jpqlbuilder.JpqlBuilder;
import org.pavel.jpqlbuilder.model.Company;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.pavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class JoinTest {
  @Test
  public void entityJoins() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select.join(employee.getDepartment()).getPathSpecifier();
    Company company = select.join(department.getCompany()).getPathSpecifier();

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
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company company = select.getPathSpecifier();
    Department department = select.join(company.getDepartments()).getPathSpecifier();

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
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select.leftJoin(employee.getDepartment()).getPathSpecifier();
    Company company = select.leftJoin(department.getCompany()).getPathSpecifier();

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
    JpqlBuilder<Department> select = JpqlBuilder.select(Department.class);
    Department department = select.getPathSpecifier();
    select.joinFetch(department.getCompany());
    select.joinFetch(department.getEmployees());

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
    JpqlBuilder<Department> select = JpqlBuilder.select(Department.class);
    Department department = select.getPathSpecifier();
    Company company = select.joinFetchWithAlias(department.getCompany()).getPathSpecifier();
    Employee employee = select.joinFetchWithAlias(department.getEmployees()).getPathSpecifier();

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
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select.join(Department.class).getPathSpecifier();

    String query = select
        .where(employee.getStatus()).is(department.getStatus())
        .getQueryString();

    String expected = "select a from test_Employee a join test_Department b where a.status = b.status";

    assertEquals(expected, query);
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void classLeftJoin() {
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select.leftJoin(Department.class).getPathSpecifier();

    String query = select
        .where(employee.getStatus()).is(department.getStatus())
        .getQueryString();

    String expected = "select a from test_Employee a left join test_Department b where a.status = b.status";

    assertEquals(expected, query);
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void collectionJoinOn() {
    JpqlBuilder<Department> select = JpqlBuilder.select(Department.class);
    Department department = select.getPathSpecifier();
    Employee employee = select
        .join(department.getEmployees())
        .on(e -> $(e.getStatus()).isNot(Status.DELETED))
        .getPathSpecifier();

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
    JpqlBuilder<Employee> select = JpqlBuilder.select(Employee.class);
    Employee employee = select.getPathSpecifier();
    Department department = select
        .join(Department.class)
        .on(d -> $(d.getName()).is(employee.getName()))
        .getPathSpecifier();

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
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();
    select.join(c.getStatus());
  }

  @Test
  public void joinSameThingMultipleTimes() {
    JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
    Company c = select.getPathSpecifier();

    Join<Department> join1 = select.join(c.getDepartments());
    Join<Department> join2 = select.join(c.getDepartments());

    assertSame(join1, join2);

    select.join(c.getDepartments()).on(d -> $(d.getStatus()).isNot(Status.DELETED));

    assertEquals("select a from test_Company a join a.departments b on b.status <> :a", select.getQueryString());
  }

  @Test
  public void joinAs() {
    JpqlBuilder<Department> select = JpqlBuilder.select(Department.class);
    Department d = select.getPathSpecifier();

    HeadOfDepartment h = select
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
