package org.test.query;

import org.junit.Test;
import org.test.model.Company;

import static org.junit.Assert.assertEquals;
import static org.test.DummyJpqlStringWriter.asString;

public class SelectClauseTest {
  @Test
  public void entity() {
    assertEquals("select a from Company a", asString(new SelectClause("a", Company.class)));
  }
}
