package org.test.operators.builders;

import org.junit.Test;
import org.test.operators.Between;
import org.test.operators.Equal;
import org.test.operators.GreaterThan;
import org.test.operators.GreaterThanOrEqual;
import org.test.operators.In;
import org.test.operators.IsNotNull;
import org.test.operators.IsNull;
import org.test.operators.LessThan;
import org.test.operators.LessThanOrEqual;
import org.test.operators.Like;
import org.test.operators.MemberOf;
import org.test.operators.Not;
import org.test.operators.NotBetween;
import org.test.operators.NotEqual;
import org.test.operators.NotIn;
import org.test.operators.NotLike;
import org.test.operators.NotMemberOf;
import org.test.operators.Operator;
import org.test.operators.Parentheses;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.test.DummyFunction.dummy;
import static org.test.DummyJpqlStringWriter.asString;

public class OperatorBuilderTest {
  @Test
  public void wrapsInParentheses() {
    ExpressionChain chain = new ExpressionChain();
    ExpressionChain wrapped = OperatorBuilder.$(chain);

    assertNotSame(wrapped, chain);
    assertTrue(wrapped.getOperator() instanceof Parentheses);
  }

  @Test
  public void wrapsInNot() {
    ExpressionChain chain = new ExpressionChain();
    ExpressionChain wrapped = OperatorBuilder.not(chain);

    assertNotSame(wrapped, chain);
    assertTrue(wrapped.getOperator() instanceof Not);
  }

  @Test
  public void is() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").is("B").getOperator();

    assertTrue(operator instanceof Equal);
    assertEquals("A = B", asString(operator));
  }

  @Test
  public void operatorIs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).is("B").getOperator();

    assertTrue(operator instanceof Equal);
    assertEquals("dummy(A) = B", asString(operator));
  }

  @Test
  public void isOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").is(dummy("B")).getOperator();

    assertTrue(operator instanceof Equal);
    assertEquals("A = dummy(B)", asString(operator));
  }

  @Test
  public void isNot() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").isNot("B").getOperator();

    assertTrue(operator instanceof NotEqual);
    assertEquals("A <> B", asString(operator));
  }

  @Test
  public void operatorIsNot() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).isNot("B").getOperator();

    assertTrue(operator instanceof NotEqual);
    assertEquals("dummy(A) <> B", asString(operator));
  }

  @Test
  public void isNotOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").isNot(dummy("B")).getOperator();

    assertTrue(operator instanceof NotEqual);
    assertEquals("A <> dummy(B)", asString(operator));
  }

  @Test
  public void isNull() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").isNull().getOperator();

    assertTrue(operator instanceof IsNull);
    assertEquals("A is null", asString(operator));
  }

  @Test
  public void operatorIsNull() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).isNull().getOperator();

    assertTrue(operator instanceof IsNull);
    assertEquals("dummy(A) is null", asString(operator));
  }

  @Test
  public void isNotNull() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").isNotNull().getOperator();

    assertTrue(operator instanceof IsNotNull);
    assertEquals("A is not null", asString(operator));
  }

  @Test
  public void operatorIsNotNull() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).isNotNull().getOperator();

    assertTrue(operator instanceof IsNotNull);
    assertEquals("dummy(A) is not null", asString(operator));
  }

  @Test
  public void between() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").between("B", "C").getOperator();

    assertTrue(operator instanceof Between);
    assertEquals("A between B and C", asString(operator));
  }

  @Test
  public void operatorBetween() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).between("B", "C").getOperator();

    assertTrue(operator instanceof Between);
    assertEquals("dummy(A) between B and C", asString(operator));
  }

  @Test
  public void betweenOperator1() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").between(dummy("B"), "C").getOperator();

    assertTrue(operator instanceof Between);
    assertEquals("A between dummy(B) and C", asString(operator));
  }

  @Test
  public void betweenOperator2() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").between("B", dummy("C")).getOperator();

    assertTrue(operator instanceof Between);
    assertEquals("A between B and dummy(C)", asString(operator));
  }

  @Test
  public void betweenOperator3() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").between(dummy("B"), dummy("C")).getOperator();

    assertTrue(operator instanceof Between);
    assertEquals("A between dummy(B) and dummy(C)", asString(operator));
  }

  @Test
  public void notBetween() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notBetween("B", "C").getOperator();

    assertTrue(operator instanceof NotBetween);
    assertEquals("A not between B and C", asString(operator));
  }

  @Test
  public void operatorNotBetween() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).notBetween("B", "C").getOperator();

    assertTrue(operator instanceof NotBetween);
    assertEquals("dummy(A) not between B and C", asString(operator));
  }

  @Test
  public void notBetweenOperator1() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notBetween(dummy("B"), "C").getOperator();

    assertTrue(operator instanceof NotBetween);
    assertEquals("A not between dummy(B) and C", asString(operator));
  }

  @Test
  public void notBetweenOperator2() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notBetween("B", dummy("C")).getOperator();

    assertTrue(operator instanceof NotBetween);
    assertEquals("A not between B and dummy(C)", asString(operator));
  }

  @Test
  public void notBetweenOperator3() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notBetween(dummy("B"), dummy("C")).getOperator();

    assertTrue(operator instanceof NotBetween);
    assertEquals("A not between dummy(B) and dummy(C)", asString(operator));
  }

  @Test
  public void in() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").in(Arrays.asList("B", "C")).getOperator();

    assertTrue(operator instanceof In);
    assertEquals("A in [B, C]", asString(operator));
  }

  @Test
  public void operatorIn() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).in(Arrays.asList("B", "C")).getOperator();

    assertTrue(operator instanceof In);
    assertEquals("dummy(A) in [B, C]", asString(operator));
  }

  @Test
  public void inVarargs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").in("B", "C").getOperator();

    assertTrue(operator instanceof In);
    assertEquals("A in [B, C]", asString(operator));
  }

  @Test
  public void operatorInVarargs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).in("B", "C").getOperator();

    assertTrue(operator instanceof In);
    assertEquals("dummy(A) in [B, C]", asString(operator));
  }

  @Test
  public void notIn() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notIn(Arrays.asList("B", "C")).getOperator();

    assertTrue(operator instanceof NotIn);
    assertEquals("A not in [B, C]", asString(operator));
  }

  @Test
  public void operatorNotIn() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).notIn(Arrays.asList("B", "C")).getOperator();

    assertTrue(operator instanceof NotIn);
    assertEquals("dummy(A) not in [B, C]", asString(operator));
  }

  @Test
  public void notInVarargs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notIn("B", "C").getOperator();

    assertTrue(operator instanceof NotIn);
    assertEquals("A not in [B, C]", asString(operator));
  }

  @Test
  public void operatorNotInVarargs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).notIn("B", "C").getOperator();

    assertTrue(operator instanceof NotIn);
    assertEquals("dummy(A) not in [B, C]", asString(operator));
  }

  @Test
  public void greaterThan() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").greaterThan("B").getOperator();

    assertTrue(operator instanceof GreaterThan);
    assertEquals("A > B", asString(operator));
  }

  @Test
  public void operatorGreaterThan() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).greaterThan("B").getOperator();

    assertTrue(operator instanceof GreaterThan);
    assertEquals("dummy(A) > B", asString(operator));
  }

  @Test
  public void greaterThanOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").greaterThan(dummy("B")).getOperator();

    assertTrue(operator instanceof GreaterThan);
    assertEquals("A > dummy(B)", asString(operator));
  }

  @Test
  public void greaterThanOrEqual() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").greaterThanOrEqual("B").getOperator();

    assertTrue(operator instanceof GreaterThanOrEqual);
    assertEquals("A >= B", asString(operator));
  }

  @Test
  public void operatorGreaterThanOrEqual() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).greaterThanOrEqual("B").getOperator();

    assertTrue(operator instanceof GreaterThanOrEqual);
    assertEquals("dummy(A) >= B", asString(operator));
  }

  @Test
  public void greaterThanOrEqualOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").greaterThanOrEqual(dummy("B")).getOperator();

    assertTrue(operator instanceof GreaterThanOrEqual);
    assertEquals("A >= dummy(B)", asString(operator));
  }

  @Test
  public void lessThan() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").lessThan("B").getOperator();

    assertTrue(operator instanceof LessThan);
    assertEquals("A < B", asString(operator));
  }

  @Test
  public void operatorLessThan() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).lessThan("B").getOperator();

    assertTrue(operator instanceof LessThan);
    assertEquals("dummy(A) < B", asString(operator));
  }

  @Test
  public void lessThanOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").lessThan(dummy("B")).getOperator();

    assertTrue(operator instanceof LessThan);
    assertEquals("A < dummy(B)", asString(operator));
  }

  @Test
  public void lessThanOrEqual() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").lessThanOrEqual("B").getOperator();

    assertTrue(operator instanceof LessThanOrEqual);
    assertEquals("A <= B", asString(operator));
  }

  @Test
  public void operatorLessThanOrEqual() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).lessThanOrEqual("B").getOperator();

    assertTrue(operator instanceof LessThanOrEqual);
    assertEquals("dummy(A) <= B", asString(operator));
  }

  @Test
  public void lessThanOrEqualOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").lessThanOrEqual(dummy("B")).getOperator();

    assertTrue(operator instanceof LessThanOrEqual);
    assertEquals("A <= dummy(B)", asString(operator));
  }

  @Test
  public void like() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").like("B").getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("A like B", asString(operator));
  }

  @Test
  public void operatorLike() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).like("B").getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("dummy(A) like B", asString(operator));
  }

  @Test
  public void likeOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").like(dummy("B")).getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("A like dummy(B)", asString(operator));
  }

  @Test
  public void likeWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").like("B", "C").getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("A like B escape C", asString(operator));
  }

  @Test
  public void operatorLikeWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).like("B", "C").getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("dummy(A) like B escape C", asString(operator));
  }

  @Test
  public void likeOperatorWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").like(dummy("B"), "C").getOperator();

    assertTrue(operator instanceof Like);
    assertEquals("A like dummy(B) escape C", asString(operator));
  }

  @Test
  public void notLike() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notLike("B").getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("A not like B", asString(operator));
  }

  @Test
  public void operatorNotLike() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).notLike("B").getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("dummy(A) not like B", asString(operator));
  }

  @Test
  public void notLikeOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notLike(dummy("B")).getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("A not like dummy(B)", asString(operator));
  }

  @Test
  public void notLikeWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notLike("B", "C").getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("A not like B escape C", asString(operator));
  }

  @Test
  public void operatorNotLikeWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, dummy("A")).notLike("B", "C").getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("dummy(A) not like B escape C", asString(operator));
  }

  @Test
  public void notLikeOperatorWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notLike(dummy("B"), "C").getOperator();

    assertTrue(operator instanceof NotLike);
    assertEquals("A not like dummy(B) escape C", asString(operator));
  }

  @Test
  public void memberOf() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").memberOf(new ArrayList<>()).getOperator();

    assertTrue(operator instanceof MemberOf);
    assertEquals("A member of []", asString(operator));
  }

  @Test
  public void notMemberOf() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notMemberOf(new ArrayList<>()).getOperator();

    assertTrue(operator instanceof NotMemberOf);
    assertEquals("A not member of []", asString(operator));
  }
}
