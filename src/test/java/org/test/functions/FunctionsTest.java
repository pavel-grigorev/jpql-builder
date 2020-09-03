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
  public void leftTrim() {
    assertEquals("trim(leading from A)", asString(new LeftTrim("A")));
  }

  @Test
  public void leftTrimWithChar() {
    assertEquals("trim(leading x from A)", asString(new LeftTrim("A", 'x')));
  }

  @Test
  public void nestedLeftTrim() {
    assertEquals("trim(leading from lower(A))", asString(new LeftTrim(new Lower("A"))));
  }

  @Test
  public void nestedLeftTrimWithChar() {
    assertEquals("trim(leading x from lower(A))", asString(new LeftTrim(new Lower("A"), 'x')));
  }

  @Test
  public void rightTrim() {
    assertEquals("trim(trailing from A)", asString(new RightTrim("A")));
  }

  @Test
  public void rightTrimWithChar() {
    assertEquals("trim(trailing x from A)", asString(new RightTrim("A", 'x')));
  }

  @Test
  public void nestedRightTrim() {
    assertEquals("trim(trailing from lower(A))", asString(new RightTrim(new Lower("A"))));
  }

  @Test
  public void nestedRightTrimWithChar() {
    assertEquals("trim(trailing x from lower(A))", asString(new RightTrim(new Lower("A"), 'x')));
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

  @Test
  public void locateNoPosition() {
    assertEquals("locate(A, B)", asString(new Locate("A", "B")));
  }

  @Test
  public void locateNoPositionNested() {
    assertEquals("locate(lower(A), B)", asString(new Locate(new Lower("A"), "B")));
    assertEquals("locate(A, lower(B))", asString(new Locate("A", new Lower("B"))));
    assertEquals("locate(lower(A), lower(B))", asString(new Locate(new Lower("A"), new Lower("B"))));
  }

  @Test
  public void locate() {
    assertEquals("locate(A, B, 1)", asString(new Locate("A", "B", 1)));
  }

  @Test
  public void locateNested() {
    assertEquals("locate(A, lower(B), 1)", asString(new Locate("A", new Lower("B"), 1)));
    assertEquals("locate(A, B, dummy(1))", asString(new Locate("A", "B", dummy(1))));
    assertEquals("locate(A, lower(B), dummy(1))", asString(new Locate("A", new Lower("B"), dummy(1))));
    assertEquals("locate(lower(A), B, 1)", asString(new Locate(new Lower("A"), "B", 1)));
    assertEquals("locate(lower(A), lower(B), 1)", asString(new Locate(new Lower("A"), new Lower("B"), 1)));
    assertEquals("locate(lower(A), B, dummy(1))", asString(new Locate(new Lower("A"), "B", dummy(1))));
    assertEquals("locate(lower(A), lower(B), dummy(1))", asString(new Locate(new Lower("A"), new Lower("B"), dummy(1))));
  }

  @Test
  public void add() {
    assertEquals("1 + 2", asString(new Add<>(1, 2)));
    assertEquals("1 + 2", asString(new Add<>(1, 2L)));
    assertEquals("1 + 2.3", asString(new Add<>(1, 2.3)));
  }

  @Test
  public void addNested() {
    assertEquals("length(A) + 2", asString(new Add<>(new Length("A"), 2)));
    assertEquals("1.2 + length(B)", asString(new Add<>(1.2, new Length("B"))));
    assertEquals("1 + 2 + 3 + 4", asString(new Add<>(new Add<>(1, 2), new Add<>(3, 4))));
    assertEquals("1 + 2 + 3 - 4", asString(new Add<>(new Add<>(1, 2), new Sub<>(3, 4))));
    assertEquals("1 + 2 + 3 * 4", asString(new Add<>(new Add<>(1, 2), new Multi<>(3, 4))));
    assertEquals("1 + 2 + 3 / 4", asString(new Add<>(new Add<>(1, 2), new Div<>(3, 4))));
  }

  @Test
  public void sub() {
    assertEquals("1 - 2", asString(new Sub<>(1, 2)));
    assertEquals("1 - 2", asString(new Sub<>(1, 2L)));
    assertEquals("1 - 2.3", asString(new Sub<>(1, 2.3)));
  }

  @Test
  public void subNested() {
    assertEquals("length(A) - 2", asString(new Sub<>(new Length("A"), 2)));
    assertEquals("1.2 - length(B)", asString(new Sub<>(1.2, new Length("B"))));
    assertEquals("1 + 2 - (3 + 4)", asString(new Sub<>(new Add<>(1, 2), new Add<>(3, 4))));
    assertEquals("1 + 2 - (3 - 4)", asString(new Sub<>(new Add<>(1, 2), new Sub<>(3, 4))));
    assertEquals("1 + 2 - 3 * 4", asString(new Sub<>(new Add<>(1, 2), new Multi<>(3, 4))));
    assertEquals("1 + 2 - 3 / 4", asString(new Sub<>(new Add<>(1, 2), new Div<>(3, 4))));
  }

  @Test
  public void multi() {
    assertEquals("1 * 2", asString(new Multi<>(1, 2)));
    assertEquals("1 * 2", asString(new Multi<>(1, 2L)));
    assertEquals("1 * 2.3", asString(new Multi<>(1, 2.3)));
  }

  @Test
  public void multiNested() {
    assertEquals("length(A) * 2", asString(new Multi<>(new Length("A"), 2)));
    assertEquals("1.2 * length(B)", asString(new Multi<>(1.2, new Length("B"))));
    assertEquals("(1 + 2) * (3 - 4)", asString(new Multi<>(new Add<>(1, 2), new Sub<>(3, 4))));
    assertEquals("1 * 2 * (3 / 4)", asString(new Multi<>(new Multi<>(1, 2), new Div<>(3, 4))));
    assertEquals("1 * 2 * 3 * 4", asString(new Multi<>(new Multi<>(1, 2), new Multi<>(3, 4))));
  }

  @Test
  public void div() {
    assertEquals("1 / 2", asString(new Div<>(1, 2)));
    assertEquals("1 / 2", asString(new Div<>(1, 2L)));
    assertEquals("1 / 2.3", asString(new Div<>(1, 2.3)));
  }

  @Test
  public void divNested() {
    assertEquals("length(A) / 2", asString(new Div<>(new Length("A"), 2)));
    assertEquals("1.2 / length(B)", asString(new Div<>(1.2, new Length("B"))));
    assertEquals("(1 + 2) / (3 - 4)", asString(new Div<>(new Add<>(1, 2), new Sub<>(3, 4))));
    assertEquals("(1 / 2) / (3 * 4)", asString(new Div<>(new Div<>(1, 2), new Multi<>(3, 4))));
  }

  @Test
  public void abs() {
    assertEquals("abs(1)", asString(new Abs<>(1)));
  }

  @Test
  public void absNested() {
    assertEquals("abs(1 - 2)", asString(new Abs<>(new Sub<>(1, 2))));
  }

  @Test
  public void mod() {
    assertEquals("mod(10 / 20)", asString(new Mod<>(new Div<>(10, 20))));
  }

  @Test
  public void sqrt() {
    assertEquals("sqrt(1)", asString(new Sqrt<>(1)));
  }

  @Test
  public void sqrtNested() {
    assertEquals("sqrt(1 + 2)", asString(new Sqrt<>(new Add<>(1, 2))));
  }

  @Test
  public void currentDate() {
    assertEquals("current_date", asString(new CurrentDate()));
  }

  @Test
  public void currentTime() {
    assertEquals("current_time", asString(new CurrentTime()));
  }

  @Test
  public void currentTimestamp() {
    assertEquals("current_timestamp", asString(new CurrentTimestamp()));
  }
}
