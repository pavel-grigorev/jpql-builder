package org.thepavel.jpqlbuilder.query;

import org.junit.Assert;
import org.junit.Test;
import org.thepavel.jpqlbuilder.DummyJpqlStringWriter;
import org.thepavel.jpqlbuilder.DummyOperator;

import static org.junit.Assert.assertEquals;

public class WhereClauseTest {
  @Test
  public void empty() {
    Assert.assertEquals("", DummyJpqlStringWriter.asString(new WhereClause()));
  }

  @Test
  public void notEmpty() {
    WhereClause where = new WhereClause();
    where.setOperator(DummyOperator.dummy("A"));

    Assert.assertEquals(" where dummy(A)", DummyJpqlStringWriter.asString(where));
  }
}
