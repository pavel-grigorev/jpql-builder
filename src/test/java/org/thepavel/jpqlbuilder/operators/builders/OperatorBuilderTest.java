package org.thepavel.jpqlbuilder.operators.builders;

import org.junit.Assert;
import org.junit.Test;
import org.thepavel.jpqlbuilder.DummyFunction;
import org.thepavel.jpqlbuilder.DummyJpqlStringWriter;
import org.thepavel.jpqlbuilder.operators.Between;
import org.thepavel.jpqlbuilder.operators.GreaterThan;
import org.thepavel.jpqlbuilder.operators.GreaterThanOrEqual;
import org.thepavel.jpqlbuilder.operators.In;
import org.thepavel.jpqlbuilder.operators.LessThan;
import org.thepavel.jpqlbuilder.operators.LessThanOrEqual;
import org.thepavel.jpqlbuilder.operators.MemberOf;
import org.thepavel.jpqlbuilder.operators.NotBetween;
import org.thepavel.jpqlbuilder.operators.NotIn;
import org.thepavel.jpqlbuilder.operators.NotLike;
import org.thepavel.jpqlbuilder.operators.NotMemberOf;
import org.thepavel.jpqlbuilder.operators.Equal;
import org.thepavel.jpqlbuilder.operators.IsNotNull;
import org.thepavel.jpqlbuilder.operators.IsNull;
import org.thepavel.jpqlbuilder.operators.Like;
import org.thepavel.jpqlbuilder.operators.Not;
import org.thepavel.jpqlbuilder.operators.NotEqual;
import org.thepavel.jpqlbuilder.operators.Operator;
import org.thepavel.jpqlbuilder.operators.Parentheses;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

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
    Assert.assertEquals("A = B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorIs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).is("B").getOperator();

    assertTrue(operator instanceof Equal);
    Assert.assertEquals("dummy(A) = B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void isOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").is(DummyFunction.dummy("B")).getOperator();

    assertTrue(operator instanceof Equal);
    Assert.assertEquals("A = dummy(B)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void isNot() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").isNot("B").getOperator();

    assertTrue(operator instanceof NotEqual);
    Assert.assertEquals("A <> B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorIsNot() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).isNot("B").getOperator();

    assertTrue(operator instanceof NotEqual);
    Assert.assertEquals("dummy(A) <> B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void isNotOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").isNot(DummyFunction.dummy("B")).getOperator();

    assertTrue(operator instanceof NotEqual);
    Assert.assertEquals("A <> dummy(B)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void isNull() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").isNull().getOperator();

    assertTrue(operator instanceof IsNull);
    Assert.assertEquals("A is null", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorIsNull() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).isNull().getOperator();

    assertTrue(operator instanceof IsNull);
    Assert.assertEquals("dummy(A) is null", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void isNotNull() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").isNotNull().getOperator();

    assertTrue(operator instanceof IsNotNull);
    Assert.assertEquals("A is not null", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorIsNotNull() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).isNotNull().getOperator();

    assertTrue(operator instanceof IsNotNull);
    Assert.assertEquals("dummy(A) is not null", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void between() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").between("B", "C").getOperator();

    assertTrue(operator instanceof Between);
    Assert.assertEquals("A between B and C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorBetween() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).between("B", "C").getOperator();

    assertTrue(operator instanceof Between);
    Assert.assertEquals("dummy(A) between B and C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void betweenOperator1() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").between(DummyFunction.dummy("B"), "C").getOperator();

    assertTrue(operator instanceof Between);
    Assert.assertEquals("A between dummy(B) and C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void betweenOperator2() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").between("B", DummyFunction.dummy("C")).getOperator();

    assertTrue(operator instanceof Between);
    Assert.assertEquals("A between B and dummy(C)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void betweenOperator3() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").between(DummyFunction.dummy("B"), DummyFunction.dummy("C")).getOperator();

    assertTrue(operator instanceof Between);
    Assert.assertEquals("A between dummy(B) and dummy(C)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void notBetween() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notBetween("B", "C").getOperator();

    assertTrue(operator instanceof NotBetween);
    Assert.assertEquals("A not between B and C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorNotBetween() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).notBetween("B", "C").getOperator();

    assertTrue(operator instanceof NotBetween);
    Assert.assertEquals("dummy(A) not between B and C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void notBetweenOperator1() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notBetween(DummyFunction.dummy("B"), "C").getOperator();

    assertTrue(operator instanceof NotBetween);
    Assert.assertEquals("A not between dummy(B) and C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void notBetweenOperator2() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notBetween("B", DummyFunction.dummy("C")).getOperator();

    assertTrue(operator instanceof NotBetween);
    Assert.assertEquals("A not between B and dummy(C)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void notBetweenOperator3() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notBetween(DummyFunction.dummy("B"), DummyFunction.dummy("C")).getOperator();

    assertTrue(operator instanceof NotBetween);
    Assert.assertEquals("A not between dummy(B) and dummy(C)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void in() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").in(Arrays.asList("B", "C")).getOperator();

    assertTrue(operator instanceof In);
    Assert.assertEquals("A in [B, C]", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorIn() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).in(Arrays.asList("B", "C")).getOperator();

    assertTrue(operator instanceof In);
    Assert.assertEquals("dummy(A) in [B, C]", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void inVarargs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").in("B", "C").getOperator();

    assertTrue(operator instanceof In);
    Assert.assertEquals("A in [B, C]", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorInVarargs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).in("B", "C").getOperator();

    assertTrue(operator instanceof In);
    Assert.assertEquals("dummy(A) in [B, C]", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void notIn() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notIn(Arrays.asList("B", "C")).getOperator();

    assertTrue(operator instanceof NotIn);
    Assert.assertEquals("A not in [B, C]", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorNotIn() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).notIn(Arrays.asList("B", "C")).getOperator();

    assertTrue(operator instanceof NotIn);
    Assert.assertEquals("dummy(A) not in [B, C]", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void notInVarargs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notIn("B", "C").getOperator();

    assertTrue(operator instanceof NotIn);
    Assert.assertEquals("A not in [B, C]", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorNotInVarargs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).notIn("B", "C").getOperator();

    assertTrue(operator instanceof NotIn);
    Assert.assertEquals("dummy(A) not in [B, C]", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void greaterThan() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").greaterThan("B").getOperator();

    assertTrue(operator instanceof GreaterThan);
    Assert.assertEquals("A > B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorGreaterThan() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).greaterThan("B").getOperator();

    assertTrue(operator instanceof GreaterThan);
    Assert.assertEquals("dummy(A) > B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void greaterThanOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").greaterThan(DummyFunction.dummy("B")).getOperator();

    assertTrue(operator instanceof GreaterThan);
    Assert.assertEquals("A > dummy(B)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void greaterThanOrEqual() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").greaterThanOrEqual("B").getOperator();

    assertTrue(operator instanceof GreaterThanOrEqual);
    Assert.assertEquals("A >= B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorGreaterThanOrEqual() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).greaterThanOrEqual("B").getOperator();

    assertTrue(operator instanceof GreaterThanOrEqual);
    Assert.assertEquals("dummy(A) >= B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void greaterThanOrEqualOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").greaterThanOrEqual(DummyFunction.dummy("B")).getOperator();

    assertTrue(operator instanceof GreaterThanOrEqual);
    Assert.assertEquals("A >= dummy(B)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void lessThan() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").lessThan("B").getOperator();

    assertTrue(operator instanceof LessThan);
    Assert.assertEquals("A < B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorLessThan() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).lessThan("B").getOperator();

    assertTrue(operator instanceof LessThan);
    Assert.assertEquals("dummy(A) < B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void lessThanOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").lessThan(DummyFunction.dummy("B")).getOperator();

    assertTrue(operator instanceof LessThan);
    Assert.assertEquals("A < dummy(B)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void lessThanOrEqual() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").lessThanOrEqual("B").getOperator();

    assertTrue(operator instanceof LessThanOrEqual);
    Assert.assertEquals("A <= B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorLessThanOrEqual() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).lessThanOrEqual("B").getOperator();

    assertTrue(operator instanceof LessThanOrEqual);
    Assert.assertEquals("dummy(A) <= B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void lessThanOrEqualOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").lessThanOrEqual(DummyFunction.dummy("B")).getOperator();

    assertTrue(operator instanceof LessThanOrEqual);
    Assert.assertEquals("A <= dummy(B)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void like() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").like("B").getOperator();

    assertTrue(operator instanceof Like);
    Assert.assertEquals("A like B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorLike() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).like("B").getOperator();

    assertTrue(operator instanceof Like);
    Assert.assertEquals("dummy(A) like B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void likeOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").like(DummyFunction.dummy("B")).getOperator();

    assertTrue(operator instanceof Like);
    Assert.assertEquals("A like dummy(B)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void likeWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").like("B", "C").getOperator();

    assertTrue(operator instanceof Like);
    Assert.assertEquals("A like B escape C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorLikeWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).like("B", "C").getOperator();

    assertTrue(operator instanceof Like);
    Assert.assertEquals("dummy(A) like B escape C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void likeOperatorWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").like(DummyFunction.dummy("B"), "C").getOperator();

    assertTrue(operator instanceof Like);
    Assert.assertEquals("A like dummy(B) escape C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void notLike() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notLike("B").getOperator();

    assertTrue(operator instanceof NotLike);
    Assert.assertEquals("A not like B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorNotLike() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).notLike("B").getOperator();

    assertTrue(operator instanceof NotLike);
    Assert.assertEquals("dummy(A) not like B", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void notLikeOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notLike(DummyFunction.dummy("B")).getOperator();

    assertTrue(operator instanceof NotLike);
    Assert.assertEquals("A not like dummy(B)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void notLikeWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notLike("B", "C").getOperator();

    assertTrue(operator instanceof NotLike);
    Assert.assertEquals("A not like B escape C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void operatorNotLikeWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, DummyFunction.dummy("A")).notLike("B", "C").getOperator();

    assertTrue(operator instanceof NotLike);
    Assert.assertEquals("dummy(A) not like B escape C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void notLikeOperatorWithEscapeChar() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notLike(DummyFunction.dummy("B"), "C").getOperator();

    assertTrue(operator instanceof NotLike);
    Assert.assertEquals("A not like dummy(B) escape C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void memberOf() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").memberOf(new ArrayList<>()).getOperator();

    assertTrue(operator instanceof MemberOf);
    Assert.assertEquals("A member of []", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void notMemberOf() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notMemberOf(new ArrayList<>()).getOperator();

    assertTrue(operator instanceof NotMemberOf);
    Assert.assertEquals("A not member of []", DummyJpqlStringWriter.asString(operator));
  }
}
