package org.test.query;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.test.DummyJpqlStringWriter.asString;
import static org.test.DummyOperator.dummy;

public class WhereClauseTest {
  @Test
  public void empty() {
    assertEquals("", asString(new WhereClause()));
  }

  @Test
  public void notEmpty() {
    WhereClause where = new WhereClause();
    where.setOperator(dummy("A"));

    assertEquals(" where dummy(A)", asString(where));
  }
}
