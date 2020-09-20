package org.pavel.jpqlbuilder.query;

import org.junit.Assert;
import org.junit.Test;
import org.pavel.jpqlbuilder.DummyJpqlStringWriter;
import org.pavel.jpqlbuilder.model.Company;

import static org.junit.Assert.assertEquals;
import static org.pavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class JoinClauseTest {
  @Test
  public void innerJoin() {
    JoinClause join = new JoinClause("a", "thing", JoinType.INNER);

    Assert.assertEquals(" join thing a", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void innerJoinOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.INNER);
    join.setOnClause($(1).is(2));

    Assert.assertEquals(" join thing a on 1 = 2", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void leftJoin() {
    JoinClause join = new JoinClause("a", "thing", JoinType.LEFT);

    Assert.assertEquals(" left join thing a", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void leftJoinOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.LEFT);
    join.setOnClause($(1).is(2));

    Assert.assertEquals(" left join thing a on 1 = 2", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void joinFetch() {
    JoinClause join = new JoinClause("a", "thing", JoinType.FETCH);

    Assert.assertEquals(" join fetch thing", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void joinFetchOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.FETCH);
    join.setOnClause($(1).is(2));

    Assert.assertEquals(" join fetch thing on 1 = 2", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void joinFetchWithAlias() {
    JoinClause join = new JoinClause("a", "thing", JoinType.FETCH_WITH_ALIAS);

    Assert.assertEquals(" join fetch thing a", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void joinFetchWithAliasOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.FETCH_WITH_ALIAS);
    join.setOnClause($(1).is(2));

    Assert.assertEquals(" join fetch thing a on 1 = 2", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void joinAs() {
    JoinClause join = new JoinClause("a", "thing", JoinType.INNER);
    join.setTreatAsType(Company.class);

    Assert.assertEquals(" join treat(thing as Company) a", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void joinAsOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.INNER);
    join.setTreatAsType(Company.class);
    join.setOnClause($(1).is(2));

    Assert.assertEquals(" join treat(thing as Company) a on 1 = 2", DummyJpqlStringWriter.asString(join));
  }
}
