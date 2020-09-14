package org.test.query;

import org.junit.Test;
import org.test.model.Company;

import static org.junit.Assert.assertEquals;
import static org.test.DummyJpqlStringWriter.asString;
import static org.test.operators.builders.OperatorBuilder.$;

public class JoinClauseTest {
  @Test
  public void innerJoin() {
    JoinClause join = new JoinClause("a", "thing", JoinType.INNER);

    assertEquals(" join thing a", asString(join));
  }

  @Test
  public void innerJoinOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.INNER);
    join.setOnClause($(1).is(2));

    assertEquals(" join thing a on 1 = 2", asString(join));
  }

  @Test
  public void leftJoin() {
    JoinClause join = new JoinClause("a", "thing", JoinType.LEFT);

    assertEquals(" left join thing a", asString(join));
  }

  @Test
  public void leftJoinOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.LEFT);
    join.setOnClause($(1).is(2));

    assertEquals(" left join thing a on 1 = 2", asString(join));
  }

  @Test
  public void joinFetch() {
    JoinClause join = new JoinClause("a", "thing", JoinType.FETCH);

    assertEquals(" join fetch thing", asString(join));
  }

  @Test
  public void joinFetchOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.FETCH);
    join.setOnClause($(1).is(2));

    assertEquals(" join fetch thing on 1 = 2", asString(join));
  }

  @Test
  public void joinFetchWithAlias() {
    JoinClause join = new JoinClause("a", "thing", JoinType.FETCH_WITH_ALIAS);

    assertEquals(" join fetch thing a", asString(join));
  }

  @Test
  public void joinFetchWithAliasOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.FETCH_WITH_ALIAS);
    join.setOnClause($(1).is(2));

    assertEquals(" join fetch thing a on 1 = 2", asString(join));
  }

  @Test
  public void joinAs() {
    JoinClause join = new JoinClause("a", "thing", JoinType.INNER);
    join.setTreatAsType(Company.class);

    assertEquals(" join treat(thing as Company) a", asString(join));
  }

  @Test
  public void joinAsOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.INNER);
    join.setTreatAsType(Company.class);
    join.setOnClause($(1).is(2));

    assertEquals(" join treat(thing as Company) a on 1 = 2", asString(join));
  }
}
