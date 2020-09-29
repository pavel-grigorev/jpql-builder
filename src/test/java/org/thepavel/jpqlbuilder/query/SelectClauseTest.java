package org.thepavel.jpqlbuilder.query;

import org.junit.Assert;
import org.junit.Test;
import org.thepavel.jpqlbuilder.DummyJpqlStringWriter;
import org.thepavel.jpqlbuilder.model.Company;

import static org.junit.Assert.assertEquals;

public class SelectClauseTest {
  @Test
  public void entity() {
    Assert.assertEquals("select a from Company a", DummyJpqlStringWriter.asString(new SelectClause("a", Company.class)));
  }
}
