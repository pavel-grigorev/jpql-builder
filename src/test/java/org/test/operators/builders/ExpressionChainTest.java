package org.test.operators.builders;

import org.junit.Test;
import org.test.operators.Operator;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.test.DummyFunction.dummy;
import static org.test.DummyJpqlStringWriter.asString;
import static org.test.operators.builders.OperatorBuilder.$;

public class ExpressionChainTest {
  @Test
  public void and() {
    ExpressionChain chain = new ExpressionChain(dummy("A"));
    Operator operator = chain.and(1).is(2).getOperator();

    assertEquals("dummy(A) and 1 = 2", asString(operator));
  }

  @Test
  public void andString() {
    ExpressionChain chain = new ExpressionChain(dummy("A"));
    Operator operator = chain.and("B").is("C").getOperator();

    assertEquals("dummy(A) and B = C", asString(operator));
  }

  @Test
  public void andOperatorString() {
    ExpressionChain chain = new ExpressionChain(dummy("A"));
    Operator operator = chain.and(dummy("B")).is("C").getOperator();

    assertEquals("dummy(A) and dummy(B) = C", asString(operator));
  }

  @Test
  public void andCollection() {
    ExpressionChain chain = new ExpressionChain(dummy("A"));
    Operator operator = chain.and(new ArrayList<>()).isEmpty().getOperator();

    assertEquals("dummy(A) and [] is empty", asString(operator));
  }

  @Test
  public void andChain() {
    ExpressionChain chain = new ExpressionChain(dummy("A"));
    Operator operator = chain.and($("B").is("C")).getOperator();

    assertEquals("dummy(A) and (B = C)", asString(operator));
  }

  @Test
  public void or() {
    ExpressionChain chain = new ExpressionChain(dummy("A"));
    Operator operator = chain.or(1).is(2).getOperator();

    assertEquals("dummy(A) or 1 = 2", asString(operator));
  }

  @Test
  public void orString() {
    ExpressionChain chain = new ExpressionChain(dummy("A"));
    Operator operator = chain.or("B").is("C").getOperator();

    assertEquals("dummy(A) or B = C", asString(operator));
  }

  @Test
  public void orOperatorString() {
    ExpressionChain chain = new ExpressionChain(dummy("A"));
    Operator operator = chain.or(dummy("B")).is("C").getOperator();

    assertEquals("dummy(A) or dummy(B) = C", asString(operator));
  }

  @Test
  public void orCollection() {
    ExpressionChain chain = new ExpressionChain(dummy("A"));
    Operator operator = chain.or(new ArrayList<>()).isNotEmpty().getOperator();

    assertEquals("dummy(A) or [] is not empty", asString(operator));
  }

  @Test
  public void orChain() {
    ExpressionChain chain = new ExpressionChain(dummy("A"));
    Operator operator = chain.or($("B").is("C")).getOperator();

    assertEquals("dummy(A) or (B = C)", asString(operator));
  }

  @Test
  public void joinEmptyChainWithOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = dummy("A");
    ExpressionChain joined = chain.join(operator);

    assertSame(chain, joined);
    assertSame(operator, joined.getOperator());
  }
}
