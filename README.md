# Intro

JpqlBuilder is a tool to dynamically build JPQL strings. JpqlBuilder provides:
- Type safety
- Fluent API

Here's how it works:

```java
JpqlBuilder<Company> select = JpqlBuilder.select(Company.class); // Company is an entity class
Company c = select.getPathSpecifier();

select.where(c.getName()).like("%TikTok%").orderBy(c.getName());

System.out.println("Query: " + select.getQueryString());
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a where a.name like :a order by a.name
Params: {a=%TikTok%}
```

The object returned by the `getPathSpecifier()` method is a proxy object created specifically for the framework purposes and must not be merged or persisted.

A simple query like the one above can be a one-liner:

```java
JpqlQuery query = JpqlBuilder
.select(Company.class).where(c -> $(c.getName()).like("%TikTok%")).orderBy(Company::getName);
```

The `$` method starts an expression.

# Currently unsupported (coming soon)

- `update` and `delete` queries
- `group by` and `having`
- `union`
- Aggregation functions (`max`, `avg`, etc.)
- Ability to select values (e.g. `select c.name from test_Company c`)
- Ability to select multiple values and/or root objects (e.g. `select c.id, c.name, c from test_Company c`)
- Subqueries

# Learn by example

Below is the model that is going to be used in all examples (getters,
setters and annotations are omitted):

```java
@Entity(name = "test_Company")
public class Company {
  private Long id;
  private Status status;
  private String name;
  private List<Department> departments;
  private Map<Long, Employee> heads;
}

@Entity(name = "test_Department")
public class Department {
  private Long id;
  private Status status;
  private String name;
  private Company company;
}

@Entity(name = "test_Employee")
public class Employee {
  private Long id;
  private Status status;
  private String name;
  private Department department;
  private Boolean headOfDepartment;
  private Date employmentDate;
}

@Entity(name = "test_HeadOfDepartment")
public class HeadOfDepartment extends Employee {
}

public enum Status {
  ACTIVE, DELETED;
}
```

## Where conditions

Nested expressions:

```java
JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
Company c = select.getPathSpecifier();

String query = select
   .where(c.getStatus()).isNot(Status.DELETED)
   .and(
       $(c.getName()).is("Google")
       .or(c.getName()).is("Apple")
   )
   .getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a where a.status <> :a and (a.name = :b or a.name = :c)
Params: {a=DELETED, b=Google, c=Apple}
```

Or like this:

```java
JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
Company c = select.getPathSpecifier();

String query = select
   .where(
       $(c.getName()).is("Google")
       .or(c.getName()).is("Apple")
   )
   .and(c.getStatus()).isNot(Status.DELETED)
   .getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a where (a.name = :a or a.name = :b) and a.status <> :c
Params: {a=Google, b=Apple, c=DELETED}
```

The not operator:

```java
import static org.test.operators.builders.OperatorBuilder.not;
import static org.test.operators.builders.OperatorBuilder.$;
…

JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
Company c = select.getPathSpecifier();

String query = select
   .where(c.getStatus()).isNot(Status.DELETED)
   .and(not(
       $(c.getName()).is("Google")
       .or(c.getName()).is("Apple")
   ))
   .getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```java
Query: select a from test_Company a where a.status <> :a and (not (a.name = :b or a.name = :c))
Params: {a=DELETED, b=Google, c=Apple}
```

## Operators

<table>
  <thead>
    <tr>
      <th>Operator</th>
      <th>Example</th>
      <th>JPQL string</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>is</td>
      <td>$(c.getName()).is("TikTok")</td>
      <td>a.name = :a</td>
    </tr>
    <tr>
      <td>isNot</td>
      <td>$(c.getName()).isNot("TikTok")</td>
      <td>a.name &lt;&gt; :a</td>
    </tr>
    <tr>
      <td>isNull</td>
      <td>$(c.getName()).isNull()</td>
      <td>a.name is null</td>
    </tr>
    <tr>
      <td>isNotNull</td>
      <td>$(c.getName()).isNotNull()</td>
      <td>a.name is not null</td>
    </tr>
    <tr>
      <td>isEmpty</td>
      <td>$(c.getDepartments()).isEmpty()</td>
      <td>a.departments is empty</td>
    </tr>
    <tr>
      <td>isNotEmpty</td>
      <td>$(c.getDepartments()).isNotEmpty()</td>
      <td>a.departments is not empty</td>
    </tr>
    <tr>
      <td rowspan="2">in</td>
      <td>$(c.getName()).in("Apple", "Google")</td>
      <td>a.name in :a</td>
    </tr>
    <tr>
      <td>$(c.getName()).in(namesCollection)</td>
      <td>a.name in :a</td>
    </tr>
    <tr>
      <td rowspan="2">notIn</td>
      <td>$(c.getName()).notIn("Apple", "Google")</td>
      <td>a.name not in :a</td>
    </tr>
    <tr>
      <td>$(c.getName()).notIn(namesCollection)</td>
      <td>a.name not in :a</td>
    </tr>
    <tr>
      <td>between</td>
      <td>$(c.getId()).between(1, 10)</td>
      <td>a.id between :a and :b</td>
    </tr>
    <tr>
      <td>notBetween</td>
      <td>$(c.getId()).notBetween(1, 10)</td>
      <td>a.id not between :a and :b</td>
    </tr>
    <tr>
      <td>greaterThan</td>
      <td>$(c.getId()).greaterThan(1)</td>
      <td>a.id &gt; :a</td>
    </tr>
    <tr>
      <td>greaterThanOrEqual</td>
      <td>$(c.getId()).greaterThanOrEqual(1)</td>
      <td>a.id &gt;= :a</td>
    </tr>
    <tr>
      <td>lessThan</td>
      <td>$(c.getId()).lessThan(1)</td>
      <td>a.id &lt; :a</td>
    </tr>
    <tr>
      <td>lessThanOrEqual</td>
      <td>$(c.getId()).lessThanOrEqual(1)</td>
      <td>a.id &lt;= :a</td>
    </tr>
    <tr>
      <td rowspan="2">like</td>
      <td>$(c.getName()).like("%TikTok%")</td>
      <td>a.name like :a</td>
    </tr>
    <tr>
      <td>$(c.getName()).like("High _%", "_")</td>
      <td>a.name like :a escape :b</td>
    </tr>
    <tr>
      <td rowspan="2">notLike</td>
      <td>$(c.getName()).notLike("%TikTok%")</td>
      <td>a.name not like :a</td>
    </tr>
    <tr>
      <td>$(c.getName()).notLike("High _%", "_")</td>
      <td>a.name not like :a escape :b</td>
    </tr>
    <tr>
      <td>not</td>
      <td>not($(c.getName()).is("TikTok"))</td>
      <td>(not (a.name = :a))</td>
    </tr>
    <tr>
      <td>memberOf</td>
      <td>$(d).memberOf(c.getDepartments())</td>
      <td>:a member of a.departments</td>
    </tr>
    <tr>
      <td>notMemberOf</td>
      <td>$(d).notMemberOf(c.getDepartments())</td>
      <td>:a not member of a.departments</td>
    </tr>
  </tbody>
</table>

## Functions

### String functions

<table>
  <thead>
    <tr>
      <th>Function</th>
      <th>Example</th>
      <th>JPQL string</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>concat</td>
      <td>$(c.getName()).like(concat("%", name, "%"))</td>
      <td>a.name like concat(:a, :b, :c)</td>
    </tr>
    <tr>
      <td>length</td>
      <td>$(length(c.getName())).is(10)</td>
      <td>length(a.name) = :a</td>
    </tr>
    <tr>
      <td rowspan="2">locate</td>
      <td>$(locate("Mr", c.getName())).is(1)</td>
      <td>locate(:a, a.name) = :b</td>
    </tr>
    <tr>
      <td>$(locate("Mr", c.getName(), 2)).is(1)</td>
      <td>locate(:a, a.name, :b) = :c</td>
    </tr>
    <tr>
      <td>lower</td>
      <td>$(lower(c.getName())).like(lower("TikTok"))</td>
      <td>lower(a.name) like lower(:a)</td>
    </tr>
    <tr>
      <td>upper</td>
      <td>$(upper(c.getName())).like(upper("TikTok"))</td>
      <td>upper(a.name) like upper(:a)</td>
    </tr>
    <tr>
      <td>regexp</td>
      <td>$(regexp(c.getName(), "^Go*")).is(Boolean.TRUE)</td>
      <td>a.name regexp :a = :b</td>
    </tr>
    <tr>
      <td rowspan="2">substring</td>
      <td>$(substring(c.getName(), 1, 2)).is("Mr")</td>
      <td>substring(a.name, :a, :b) = :c</td>
    </tr>
    <tr>
      <td>$(substring(c.getName(), 4)).is("Smith")</td>
      <td>substring(a.name, :a) = :b</td>
    </tr>
    <tr>
      <td rowspan="2">trim</td>
      <td>$(trim(c.getName())).like(trim("TikTok"))</td>
      <td>trim(a.name) like trim(:a)</td>
    </tr>
    <tr>
      <td>$(trim(c.getName(), '-')).like(trim("TikTok"))</td>
      <td>trim(:a from a.name) like trim(:b)</td>
    </tr>
    <tr>
      <td rowspan="2">ltrim</td>
      <td>$(ltrim(c.getName())).like(ltrim("TikTok"))</td>
      <td>trim(leading from a.name) like trim(leading from :a)</td>
    </tr>
    <tr>
      <td>$(ltrim(c.getName(), '-')).like(ltrim("TikTok"))</td>
      <td>trim(leading :a from a.name) like trim(leading from :b)</td>
    </tr>
    <tr>
      <td rowspan="2">rtrim</td>
      <td>$(rtrim(c.getName())).like(rtrim("TikTok"))</td>
      <td>trim(trailing from a.name) like trim(trailing from :a)</td>
    </tr>
    <tr>
      <td>$(rtrim(c.getName(), '-')).like(rtrim("TikTok"))</td>
      <td>trim(trailing :a from a.name) like trim(trailing from :b)</td>
    </tr>
  </tbody>
</table>

### Arithmetic functions

<table>
  <thead>
    <tr>
      <th>Function</th>
      <th>Example</th>
      <th>JPQL string</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>add</td>
      <td>$(add(length(c.getName()), 5)).is(10)</td>
      <td>length(a.name) + :a = :b</td>
    </tr>
    <tr>
      <td>sub</td>
      <td>$(sub(length(c.getName()), 5)).is(10)</td>
      <td>length(a.name) - :a = :b</td>
    </tr>
    <tr>
      <td>multi</td>
      <td>$(multi(length(c.getName()), 5)).is(10)</td>
      <td>length(a.name) * :a = :b</td>
    </tr>
    <tr>
      <td>div</td>
      <td>$(div(length(c.getName()), 5)).is(10)</td>
      <td>length(a.name) / :a = :b</td>
    </tr>
    <tr>
      <td>abs</td>
      <td>$(abs(sub(length(c.getName()), 5))).is(5)</td>
      <td>abs(length(a.name) - :a) = :b</td>
    </tr>
    <tr>
      <td>mod</td>
      <td>$(mod(div(length(c.getName()), 10))).is(5)</td>
      <td>mod(length(a.name) / :a) = :b</td>
    </tr>
    <tr>
      <td>sqrt</td>
      <td>$(length(c.getName())).is(sqrt(100))</td>
      <td>length(a.name) = sqrt(:a)</td>
    </tr>
  </tbody>
</table>

### Datetime functions

<table>
  <thead>
    <tr>
      <th>Function</th>
      <th>Example</th>
      <th>JPQL string</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>current_date</td>
      <td>$(e.getStartDate()).is(currentDate())</td>
      <td>a.startDate = current_date</td>
    </tr>
    <tr>
      <td>current_time</td>
      <td>$(e.getStartDate()).is(currentTime())</td>
      <td>a.startDate = current_time</td>
    </tr>
    <tr>
      <td>current_timestamp</td>
      <td>$(e.getStartDate()).is(currentTimestamp())</td>
      <td>a.startDate = current_timestamp</td>
    </tr>
    <tr>
      <td>extract</td>
      <td>$(extract(e.getStartDate(), Extract.Part.YEAR)).is(extract(currentDate(), Extract.Part.YEAR))</td>
      <td>extract(YEAR from a.startDate) = extract(YEAR from current_date)</td>
    </tr>
  </tbody>
</table>

### Assorted functions

<table>
  <thead>
    <tr>
      <th>Function</th>
      <th>Example</th>
      <th>JPQL string</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>cast</td>
      <td>$(cast(e.getStatus(), Cast.Type.STRING)).is("active")</td>
      <td>cast(a.status string) = :a</td>
    </tr>
    <tr>
      <td>coalesce</td>
      <td>$(length(coalesce(e.getName(), "dummy"))).greaterThan(4)</td>
      <td>length(coalesce(a.name, :a)) &gt; :b</td>
    </tr>
    <tr>
      <td>nullif</td>
      <td>$(nullif(lower(e.getName()), "google")).isNull()</td>
      <td>nullif(lower(a.name), :a) is null</td>
    </tr>
    <tr>
      <td>index</td>
      <td>select.join(c.getDepartments()).on(d -> $(index(d)).between(1, 10))</td>
      <td>join a.departments b on index(b) between :a and :b</td>
    </tr>
    <tr>
      <td>key</td>
      <td>select.join(c.getHeads()).on(h -> $(key(h)).between(1L, 10L))</td>
      <td>join a.heads b on key(b) between :a and :b</td>
    </tr>
    <tr>
      <td>value</td>
      <td>select.join(c.getHeads()).on(h -> $(value(h).getStatus()).isNot(Status.DELETED))</td>
      <td>join a.heads b on value(b).status &lt;&gt; :a</td>
    </tr>
    <tr>
      <td>size</td>
      <td>$(size(c.getDepartments())).greaterThan(5)</td>
      <td>size(a.departments) &gt; :a</td>
    </tr>
    <tr>
      <td>type</td>
      <td>$(type(c)).is(Company.class)</td>
      <td>type(a) = test_Company</td>
    </tr>
    <tr>
      <td>function</td>
      <td>$(function("coalesce", c.getName(), "dummy")).is("x")</td>
      <td>function('coalesce', a.name, :a) = :b</td>
    </tr>
    <tr>
      <td>func</td>
      <td>$(func("coalesce", c.getName(), "dummy")).is("x")</td>
      <td>func('coalesce', a.name, :a) = :b</td>
    </tr>
    <tr>
      <td>sql</td>
      <td>$(sql("cast(? as char)", c.getStatus())).is("active")</td>
      <td>sql('cast(? as char)', a.status) = :a</td>
    </tr>
    <tr>
      <td>column</td>
      <td>$(column("rowid", c)).lessThan(100)</td>
      <td>column('rowid', a) &lt; :a</td>
    </tr>
  </tbody>
</table>

### Case expression

There are two types of the expression. The first one is:

```java
JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
Company c = select.getPathSpecifier();

JpqlFunction<Integer> code = _case(lower(c.getName()))
   .when("google").then(1)
   .when("apple").then(2)
   .orElse(0);

String query = select.where(code).is(0).getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a where case lower(a.name) when :a then :b when :c then :d else :e end = :f
Params: {a=google, b=1, c=apple, d=2, e=0, f=0}
```

Another way to build the case expression is:

```java
JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
Company c = select.getPathSpecifier();

JpqlFunction<Integer> code = _case()
   .when(c.getName()).is("Google").then(1)
   .when(c.getName()).is("Apple").then(2)
   .orElse(0);

String query = select.where(code).is(0).getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a where case when a.name = :a then :b when a.name = :c then :d else :e end = :f
Params: {a=Google, b=1, c=Apple, d=2, e=0, f=0}
```

## Order by

```java
JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
Company c = select.getPathSpecifier();

String query = select
   .orderBy(c.getStatus())
   .orderBy(c.getName()).desc()
   .orderBy(c.getId()).asc()
   .getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a order by a.status, a.name desc, a.id asc
Params: {}
```

Nulls first / nulls last:

```java
JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
Company c = select.getPathSpecifier();

String query = select
   .orderBy(c.getStatus()).nullsLast()
   .orderBy(c.getName()).desc().nullsFirst()
   .getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a order by a.status nulls last, a.name desc nulls first
Params: {}
```

## Joins

Joining a collection relationship:

```java
JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
Company c = select.getPathSpecifier();
Department d = select.join(c.getDepartments()).getPathSpecifier();

String query = select
   .where(c.getStatus()).isNot(Status.DELETED)
   .and(d.getStatus()).isNot(Status.DELETED)
   .getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a join a.departments b where a.status <> :a and b.status <> :b
Params: {a=DELETED, b=DELETED}
```

Joining on a condition:

```java
JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
Company c = select.getPathSpecifier();
select
   .join(c.getDepartments())
   .on(d -> $(d.getStatus()).isNot(Status.DELETED));

String query = select
   .where(c.getStatus()).isNot(Status.DELETED)
   .getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a join a.departments b on b.status <> :a where a.status <> :b
Params: {a=DELETED, b=DELETED}
```

Joining a *-to-one relationship:

```java
JpqlBuilder<Department> select = JpqlBuilder.select(Department.class);
Department d = select.getPathSpecifier();
Company c = select.join(d.getCompany()).getPathSpecifier();

String query = select
   .where(c.getName()).is("TikTok")
   .orderBy(d.getName()).desc()
   .getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Department a join a.company b where b.name = :a order by a.name desc
Params: {a=TikTok}
```

Joining an entity class:

```java
JpqlBuilder<Department> select = JpqlBuilder.select(Department.class);
Department d = select.getPathSpecifier();
Company c = select.join(Company.class).on(e -> $(d.getCompany()).is(e)).getPathSpecifier();

String query = select
   .where(c.getName()).is("TikTok")
   .orderBy(d.getName()).desc()
   .getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Department a join test_Company b on a.company = b where b.name = :a order by a.name desc
Params: {a=TikTok}
```

Left join:

```java
JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
Company c = select.getPathSpecifier();
Department d = select
   .leftJoin(c.getDepartments())
   .on(e -> $(e.getStatus()).isNot(Status.DELETED))
   .getPathSpecifier();

String query = select
   .where(c.getStatus()).isNot(Status.DELETED)
   .and(d.getName()).like("%IT%")
   .getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a left join a.departments b on b.status <> :a where a.status <> :b and b.name like :c
Params: {a=DELETED, b=DELETED, c=%IT%}
```

Join fetch:

```java
JpqlBuilder<Company> select = JpqlBuilder.select(Company.class);
Company c = select.getPathSpecifier();
select.joinFetch(c.getDepartments());

String query = select.where(c.getStatus()).isNot(Status.DELETED).getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a join fetch a.departments where a.status <> :a
Params: {a=DELETED}
```

Join fetch with alias:

```java
JpqlBuilder<Department> select = JpqlBuilder.select(Department.class);
Department d = select.getPathSpecifier();
Company c = select.joinFetchWithAlias(d.getCompany()).getPathSpecifier();

String query = select.orderBy(c.getName()).orderBy(d.getName()).getQueryString();

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Department a join fetch a.company b order by b.name, a.name
Params: {}
```

Casting a joined entity (using the `treat` function):

```java
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

System.out.println("Query: " + query);
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Department a join treat(a.employees as test_HeadOfDepartment) b on b.headOfDepartment = :a where b.status <> :b
Params: {a=true, b=DELETED}
```

## Building a query dynamically

```java
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

select.join(c.getDepartments());

System.out.println("Query: " + select.getQueryString());
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a join a.departments b where a.name = :a or a.name = :b
Params: {a=Google, b=Apple}
```

One more example:

```java
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

System.out.println("Query: " + select.getQueryString());
System.out.println("Params: " + select.getParameters());
```

Output:

```
Query: select a from test_Company a join a.departments b on b.status <> :a where (a.name = :b or a.name = :c) and a.status <> :d
Params: {a=DELETED, b=Google, c=Apple, d=DELETED}
```
