package org.test.functions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.test.operators.DummyJpqlStringWriter.asString;

public class FunctionsTest {
  @Test
  public void lower() {
    assertEquals("lower(A)", asString(new Lower("A")));
  }

  @Test
  public void upper() {
    assertEquals("upper(A)", asString(new Upper("A")));
  }
}
