package org.test.operators.builders;

import org.junit.Test;
import org.test.operators.Like;
import org.test.operators.NotLike;
import org.test.operators.Operator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.test.DummyJpqlStringWriter.asString;
import static org.test.DummyOperator.dummy;

public class StringOperatorBuilderTest {
  @Test
  public void like() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, "A").like("B").getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("A like B", asString(operator));
  }

  @Test
  public void operatorLike() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, dummy("A")).like("B").getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("dummy(A) like B", asString(operator));
  }

  @Test
  public void likeOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, "A").like(dummy("B")).getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("A like dummy(B)", asString(operator));
  }

  @Test
  public void operatorLikeOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, dummy("A")).like(dummy("B")).getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("dummy(A) like dummy(B)", asString(operator));
  }

  @Test
  public void likeWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, "A").like("B", "C").getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("A like B escape C", asString(operator));
  }

  @Test
  public void operatorLikeWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, dummy("A")).like("B", "C").getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("dummy(A) like B escape C", asString(operator));
  }

  @Test
  public void likeOperatorWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, "A").like(dummy("B"), "C").getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("A like dummy(B) escape C", asString(operator));
  }

  @Test
  public void operatorLikeOperatorWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, dummy("A")).like(dummy("B"), "C").getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("dummy(A) like dummy(B) escape C", asString(operator));
  }

  @Test
  public void notLike() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, "A").notLike("B").getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("A not like B", asString(operator));
  }

  @Test
  public void operatorNotLike() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, dummy("A")).notLike("B").getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("dummy(A) not like B", asString(operator));
  }

  @Test
  public void notLikeOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, "A").notLike(dummy("B")).getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("A not like dummy(B)", asString(operator));
  }

  @Test
  public void operatorNotLikeOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, dummy("A")).notLike(dummy("B")).getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("dummy(A) not like dummy(B)", asString(operator));
  }

  @Test
  public void notLikeWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, "A").notLike("B", "C").getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("A not like B escape C", asString(operator));
  }

  @Test
  public void operatorNotLikeWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, dummy("A")).notLike("B", "C").getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("dummy(A) not like B escape C", asString(operator));
  }

  @Test
  public void notLikeOperatorWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, "A").notLike(dummy("B"), "C").getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("A not like dummy(B) escape C", asString(operator));
  }

  @Test
  public void operatorNotLikeOperatorWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new StringOperatorBuilder<>(chain, dummy("A")).notLike(dummy("B"), "C").getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("dummy(A) not like dummy(B) escape C", asString(operator));
  }
}
