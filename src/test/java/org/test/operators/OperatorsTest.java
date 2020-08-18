package org.test.operators;

import org.junit.Assert;
import org.junit.Test;
import org.test.querystring.JpqlStringWriter;

import java.util.Arrays;

public class OperatorsTest {
  @Test
  public void and() {
    test("A and B", new And<>("A", "B"));
  }

  @Test
  public void between() {
    test("A between B and C", new Between<>("A", "B", "C"));
  }

  @Test
  public void equal() {
    test("A = B", new Equal<>("A", "B"));
  }

  @Test
  public void equalsNull() {
    test("A is null", new Equal<>("A", null));
  }

  @Test
  public void greaterThan() {
    test("A > B", new GreaterThan<>("A", "B"));
  }

  @Test
  public void greaterThanOrEqual() {
    test("A >= B", new GreaterThanOrEqual<>("A", "B"));
  }

  @Test
  public void in() {
    test("A in [B, C]", new In<>("A", Arrays.asList("B", "C")));
  }

  @Test
  public void isNotNull() {
    test("A is not null", new IsNotNull<>("A"));
  }

  @Test
  public void isNull() {
    test("A is null", new IsNull<>("A"));
  }

  @Test
  public void lessThan() {
    test("A < B", new LessThan<>("A", "B"));
  }

  @Test
  public void lessThanOrEqual() {
    test("A <= B", new LessThanOrEqual<>("A", "B"));
  }

  @Test
  public void like() {
    test("A like B", new Like("A", "B"));
  }

  @Test
  public void likeWithEscapeChar() {
    test("A like B escape C", new Like("A", "B", "C"));
  }

  @Test
  public void not() {
    test("not (A = B)", new Not(new Equal<>("A", "B")));
  }

  @Test
  public void notBetween() {
    test("A not between B and C", new NotBetween<>("A", "B", "C"));
  }

  @Test
  public void notEqual() {
    test("A <> B", new NotEqual<>("A", "B"));
  }

  @Test
  public void notEqualNull() {
    test("A is not null", new NotEqual<>("A", null));
  }

  @Test
  public void notIn() {
    test("A not in [B, C]", new NotIn<>("A", Arrays.asList("B", "C")));
  }

  @Test
  public void notLike() {
    test("A not like B", new NotLike("A", "B"));
  }

  @Test
  public void notLikeWithEscapeChar() {
    test("A not like B escape C", new NotLike("A", "B", "C"));
  }

  @Test
  public void or() {
    test("A or B", new Or<>("A", "B"));
  }

  @Test
  public void parentheses() {
    test("(A = B)", new Parentheses(new Equal<>("A", "B")));
  }

  private void test(String expected, Operator operator) {
    JpqlStringWriter stringWriter = new DummyJpqlStringWriter();
    operator.writeTo(stringWriter);
    Assert.assertEquals(expected, stringWriter.toString());
  }
}
