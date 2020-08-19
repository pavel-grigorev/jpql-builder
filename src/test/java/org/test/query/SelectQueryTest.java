package org.test.query;

import org.junit.Test;
import org.test.entities.Advertiser;

import static org.junit.Assert.assertEquals;
import static org.test.DummyJpqlStringWriter.asString;
import static org.test.DummyOperator.dummy;

public class SelectQueryTest {
  @Test
  public void minimal() {
    SelectQuery select = new SelectQuery("a", Advertiser.class);

    assertEquals("select a from Advertiser a", asString(select));
  }

  @Test
  public void oneJoin() {
    SelectQuery select = new SelectQuery("a", Advertiser.class);
    select.addJoin(new JoinClause("b", "a.campaigns", JoinType.INNER));

    assertEquals("select a from Advertiser a join a.campaigns b", asString(select));
  }

  @Test
  public void multipleJoins() {
    SelectQuery select = new SelectQuery("a", Advertiser.class);
    select.addJoin(new JoinClause("b", "a.campaigns", JoinType.INNER));
    select.addJoin(new JoinClause("c", "b.adGroups", JoinType.INNER));
    select.addJoin(new JoinClause("d", "c.bids", JoinType.INNER));

    assertEquals("select a from Advertiser a join a.campaigns b join b.adGroups c join c.bids d", asString(select));
  }

  @Test
  public void where() {
    SelectQuery select = new SelectQuery("a", Advertiser.class);
    select.setWhere(dummy("A"));

    assertEquals("select a from Advertiser a where dummy(A)", asString(select));
  }

  @Test
  public void oneOrderBy() {
    SelectQuery select = new SelectQuery("a", Advertiser.class);
    select.addOrderBy("a.name");

    assertEquals("select a from Advertiser a order by a.name", asString(select));
  }

  @Test
  public void oneOrderByAsc() {
    SelectQuery select = new SelectQuery("a", Advertiser.class);
    select.addOrderBy("a.name");
    select.setOrderAsc();

    assertEquals("select a from Advertiser a order by a.name asc", asString(select));
  }

  @Test
  public void oneOrderByDesc() {
    SelectQuery select = new SelectQuery("a", Advertiser.class);
    select.addOrderBy("a.name");
    select.setOrderDesc();

    assertEquals("select a from Advertiser a order by a.name desc", asString(select));
  }

  @Test
  public void oneOrderByNullsFirst() {
    SelectQuery select = new SelectQuery("a", Advertiser.class);
    select.addOrderBy("a.name");
    select.setOrderNullsFirst();

    assertEquals("select a from Advertiser a order by a.name nulls first", asString(select));
  }

  @Test
  public void oneOrderByNullsLast() {
    SelectQuery select = new SelectQuery("a", Advertiser.class);
    select.addOrderBy("a.name");
    select.setOrderNullsLast();

    assertEquals("select a from Advertiser a order by a.name nulls last", asString(select));
  }

  @Test
  public void multipleOrderBy() {
    SelectQuery select = new SelectQuery("a", Advertiser.class);
    select.addOrderBy("a.name");
    select.setOrderDesc();
    select.setOrderNullsFirst();
    select.addOrderBy("a.id");
    select.setOrderAsc();
    select.setOrderNullsLast();
    select.addOrderBy("a.createTime");

    assertEquals("select a from Advertiser a order by a.name desc nulls first, a.id asc nulls last, a.createTime", asString(select));
  }

  @Test
  public void all() {
    SelectQuery select = new SelectQuery("a", Advertiser.class);
    select.addJoin(new JoinClause("b", "a.campaigns", JoinType.LEFT));
    select.addJoin(new JoinClause("c", "b.adGroups", JoinType.LEFT));
    select.setWhere(dummy("A"));
    select.addOrderBy("a.name");
    select.setOrderDesc();
    select.setOrderNullsFirst();
    select.addOrderBy("a.id");

    assertEquals("select a from Advertiser a left join a.campaigns b left join b.adGroups c where dummy(A) order by a.name desc nulls first, a.id", asString(select));
  }
}
