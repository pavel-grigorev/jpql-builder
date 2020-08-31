package org.test.functions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.test.DummyJpqlStringWriter.asString;

public class FunctionsTest {
  @Test
  public void lower() {
    assertEquals("lower(A)", asString(new Lower("A")));
  }

  @Test
  public void upper() {
    assertEquals("upper(A)", asString(new Upper("A")));
  }

  @Test
  public void upperLower() {
    assertEquals("upper(lower(A))", asString(new Upper(new Lower("A"))));
  }

  @Test
  public void lowerUpper() {
    assertEquals("lower(upper(A))", asString(new Lower(new Upper("A"))));
  }

  @Test
  public void trim() {
    assertEquals("trim(A)", asString(new Trim("A")));
  }

  @Test
  public void trimWithChar() {
    assertEquals("trim(x from A)", asString(new Trim("A", 'x')));
  }

  @Test
  public void nestedTrim() {
    assertEquals("trim(lower(A))", asString(new Trim(new Lower("A"))));
  }

  @Test
  public void nestedTrimWithChar() {
    assertEquals("trim(x from lower(A))", asString(new Trim(new Lower("A"), 'x')));
  }

  @Test
  public void ltrim() {
    assertEquals("trim(leading from A)", asString(new Ltrim("A")));
  }

  @Test
  public void ltrimWithChar() {
    assertEquals("trim(leading x from A)", asString(new Ltrim("A", 'x')));
  }

  @Test
  public void nestedLtrim() {
    assertEquals("trim(leading from lower(A))", asString(new Ltrim(new Lower("A"))));
  }

  @Test
  public void nestedLtrimWithChar() {
    assertEquals("trim(leading x from lower(A))", asString(new Ltrim(new Lower("A"), 'x')));
  }

  @Test
  public void rtrim() {
    assertEquals("trim(trailing from A)", asString(new Rtrim("A")));
  }

  @Test
  public void rtrimWithChar() {
    assertEquals("trim(trailing x from A)", asString(new Rtrim("A", 'x')));
  }

  @Test
  public void nestedRtrim() {
    assertEquals("trim(trailing from lower(A))", asString(new Rtrim(new Lower("A"))));
  }

  @Test
  public void nestedRtrimWithChar() {
    assertEquals("trim(trailing x from lower(A))", asString(new Rtrim(new Lower("A"), 'x')));
  }
}
