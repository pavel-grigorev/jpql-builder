package org.test.functions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.test.DummyFunction.dummy;
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

  @Test
  public void concatSingle() {
    assertEquals("concat(A)", asString(new Concat("A")));
  }

  @Test
  public void concatSingleNested() {
    assertEquals("concat(lower(A))", asString(new Concat(new Lower("A"))));
  }

  @Test
  public void concatMulti() {
    assertEquals("concat(A, B, C)", asString(new Concat("A", "B", "C")));
  }

  @Test
  public void concatMultiNested() {
    assertEquals("concat(lower(A), upper(B))", asString(new Concat(new Lower("A"), new Upper("B"))));
  }

  @Test
  public void substringNoLength() {
    assertEquals("substring(A, 1)", asString(new Substring("A", 1)));
  }

  @Test
  public void substringNoLengthNested() {
    assertEquals("substring(lower(A), 1)", asString(new Substring(new Lower("A"), 1)));
    assertEquals("substring(A, dummy(1))", asString(new Substring("A", dummy(1))));
    assertEquals("substring(lower(A), dummy(1))", asString(new Substring(new Lower("A"), dummy(1))));
  }

  @Test
  public void substring() {
    assertEquals("substring(A, 1, 2)", asString(new Substring("A", 1, 2)));
  }

  @Test
  public void substringNested() {
    assertEquals("substring(A, dummy(1), 2)", asString(new Substring("A", dummy(1), 2)));
    assertEquals("substring(A, 1, dummy(2))", asString(new Substring("A", 1, dummy(2))));
    assertEquals("substring(A, dummy(1), dummy(2))", asString(new Substring("A", dummy(1), dummy(2))));
    assertEquals("substring(lower(A), 1, 2)", asString(new Substring(new Lower("A"), 1, 2)));
    assertEquals("substring(lower(A), dummy(1), 2)", asString(new Substring(new Lower("A"), dummy(1), 2)));
    assertEquals("substring(lower(A), 1, dummy(2))", asString(new Substring(new Lower("A"), 1, dummy(2))));
    assertEquals("substring(lower(A), dummy(1), dummy(2))", asString(new Substring(new Lower("A"), dummy(1), dummy(2))));
  }

  @Test
  public void length() {
    assertEquals("length(A)", asString(new Length("A")));
  }

  @Test
  public void lengthNested() {
    assertEquals("length(lower(A))", asString(new Length(new Lower("A"))));
  }
}
