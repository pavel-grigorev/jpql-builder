package org.test.query;

import org.junit.Test;
import org.test.model.Company;
import org.test.model.Employee;

import static org.junit.Assert.assertEquals;
import static org.test.DummyJpqlStringWriter.asString;
import static org.test.DummyOperator.dummy;

public class SelectQueryTest {
  @Test
  public void minimal() {
    SelectQuery select = new SelectQuery("a", Company.class);

    assertEquals("select a from Company a", asString(select));
  }

  @Test
  public void oneJoin() {
    SelectQuery select = new SelectQuery("a", Company.class);
    select.addJoin(new JoinClause("b", "a.departments", JoinType.INNER));

    assertEquals("select a from Company a join a.departments b", asString(select));
  }

  @Test
  public void multipleJoins() {
    SelectQuery select = new SelectQuery("a", Company.class);
    select.addJoin(new JoinClause("b", "a.departments", JoinType.INNER));
    select.addJoin(new JoinClause("c", "b.employees", JoinType.INNER));

    assertEquals("select a from Company a join a.departments b join b.employees c", asString(select));
  }

  @Test
  public void where() {
    SelectQuery select = new SelectQuery("a", Company.class);
    select.setWhere(dummy("A"));

    assertEquals("select a from Company a where dummy(A)", asString(select));
  }

  @Test
  public void oneOrderBy() {
    SelectQuery select = new SelectQuery("a", Company.class);
    select.addOrderBy("a.name");

    assertEquals("select a from Company a order by a.name", asString(select));
  }

  @Test
  public void oneOrderByAsc() {
    SelectQuery select = new SelectQuery("a", Company.class);
    select.addOrderBy("a.name");
    select.setOrderAsc();

    assertEquals("select a from Company a order by a.name asc", asString(select));
  }

  @Test
  public void oneOrderByDesc() {
    SelectQuery select = new SelectQuery("a", Company.class);
    select.addOrderBy("a.name");
    select.setOrderDesc();

    assertEquals("select a from Company a order by a.name desc", asString(select));
  }

  @Test
  public void oneOrderByNullsFirst() {
    SelectQuery select = new SelectQuery("a", Company.class);
    select.addOrderBy("a.name");
    select.setOrderNullsFirst();

    assertEquals("select a from Company a order by a.name nulls first", asString(select));
  }

  @Test
  public void oneOrderByNullsLast() {
    SelectQuery select = new SelectQuery("a", Company.class);
    select.addOrderBy("a.name");
    select.setOrderNullsLast();

    assertEquals("select a from Company a order by a.name nulls last", asString(select));
  }

  @Test
  public void multipleOrderBy() {
    SelectQuery select = new SelectQuery("a", Company.class);
    select.addOrderBy("a.name");
    select.setOrderDesc();
    select.setOrderNullsFirst();
    select.addOrderBy("a.id");
    select.setOrderAsc();
    select.setOrderNullsLast();
    select.addOrderBy("a.createTime");

    assertEquals("select a from Company a order by a.name desc nulls first, a.id asc nulls last, a.createTime", asString(select));
  }

  @Test
  public void all() {
    SelectQuery select = new SelectQuery("a", Company.class);
    select.addJoin(new JoinClause("b", "a.departments", JoinType.LEFT));
    select.addJoin(new JoinClause("c", "b.employees", JoinType.LEFT));
    select.setWhere(dummy("A"));
    select.addOrderBy("a.name");
    select.setOrderDesc();
    select.setOrderNullsFirst();
    select.addOrderBy("a.id");

    assertEquals("select a from Company a left join a.departments b left join b.employees c where dummy(A) order by a.name desc nulls first, a.id", asString(select));
  }
}
