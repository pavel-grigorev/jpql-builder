package org.pavel.jpqlbuilder.query;

import org.junit.Assert;
import org.junit.Test;
import org.pavel.jpqlbuilder.DummyJpqlStringWriter;
import org.pavel.jpqlbuilder.DummyOperator;

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
