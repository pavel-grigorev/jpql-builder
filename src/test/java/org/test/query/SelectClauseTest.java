package org.test.query;

import org.junit.Test;
import org.test.entities.Advertiser;

import static org.junit.Assert.assertEquals;
import static org.test.DummyJpqlStringWriter.asString;

public class SelectClauseTest {
  @Test
  public void entity() {
    assertEquals("select a from Advertiser a", asString(new SelectClause("a", Advertiser.class)));
  }
}
