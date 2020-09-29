package org.thepavel.jpqlbuilder.operators.builders;

import org.junit.Assert;
import org.junit.Test;
import org.thepavel.jpqlbuilder.DummyFunction;
import org.thepavel.jpqlbuilder.DummyJpqlStringWriter;
import org.thepavel.jpqlbuilder.operators.Operator;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class ExpressionChainTest {
  @Test
  public void and() {
    ExpressionChain chain = new ExpressionChain(DummyFunction.dummy("A"));
    Operator operator = chain.and(1).is(2).getOperator();

    Assert.assertEquals("dummy(A) and 1 = 2", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void andString() {
    ExpressionChain chain = new ExpressionChain(DummyFunction.dummy("A"));
    Operator operator = chain.and("B").is("C").getOperator();

    Assert.assertEquals("dummy(A) and B = C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void andOperatorString() {
    ExpressionChain chain = new ExpressionChain(DummyFunction.dummy("A"));
    Operator operator = chain.and(DummyFunction.dummy("B")).is("C").getOperator();

    Assert.assertEquals("dummy(A) and dummy(B) = C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void andCollection() {
    ExpressionChain chain = new ExpressionChain(DummyFunction.dummy("A"));
    Operator operator = chain.and(new ArrayList<>()).isEmpty().getOperator();

    Assert.assertEquals("dummy(A) and [] is empty", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void andChain() {
    ExpressionChain chain = new ExpressionChain(DummyFunction.dummy("A"));
    Operator operator = chain.and($("B").is("C")).getOperator();

    Assert.assertEquals("dummy(A) and (B = C)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void or() {
    ExpressionChain chain = new ExpressionChain(DummyFunction.dummy("A"));
    Operator operator = chain.or(1).is(2).getOperator();

    Assert.assertEquals("dummy(A) or 1 = 2", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void orString() {
    ExpressionChain chain = new ExpressionChain(DummyFunction.dummy("A"));
    Operator operator = chain.or("B").is("C").getOperator();

    Assert.assertEquals("dummy(A) or B = C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void orOperatorString() {
    ExpressionChain chain = new ExpressionChain(DummyFunction.dummy("A"));
    Operator operator = chain.or(DummyFunction.dummy("B")).is("C").getOperator();

    Assert.assertEquals("dummy(A) or dummy(B) = C", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void orCollection() {
    ExpressionChain chain = new ExpressionChain(DummyFunction.dummy("A"));
    Operator operator = chain.or(new ArrayList<>()).isNotEmpty().getOperator();

    Assert.assertEquals("dummy(A) or [] is not empty", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void orChain() {
    ExpressionChain chain = new ExpressionChain(DummyFunction.dummy("A"));
    Operator operator = chain.or($("B").is("C")).getOperator();

    Assert.assertEquals("dummy(A) or (B = C)", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void joinEmptyChainWithOperator() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = DummyFunction.dummy("A");
    ExpressionChain joined = chain.join(operator);

    assertSame(chain, joined);
    assertSame(operator, joined.getOperator());
  }
}
