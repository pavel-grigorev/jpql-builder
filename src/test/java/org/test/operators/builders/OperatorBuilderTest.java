package org.test.operators.builders;

import org.junit.Assert;
import org.junit.Test;
import org.test.operators.Between;
import org.test.operators.DummyJpqlStringWriter;
import org.test.operators.Equal;
import org.test.operators.GreaterThan;
import org.test.operators.GreaterThanOrEqual;
import org.test.operators.In;
import org.test.operators.IsNotNull;
import org.test.operators.IsNull;
import org.test.operators.LessThan;
import org.test.operators.LessThanOrEqual;
import org.test.operators.Not;
import org.test.operators.NotBetween;
import org.test.operators.NotEqual;
import org.test.operators.NotIn;
import org.test.operators.Operator;
import org.test.operators.Parentheses;
import org.test.operators.UnaryOperator;
import org.test.querystring.JpqlStringWriter;

import java.util.Arrays;

public class OperatorBuilderTest {
  @Test
  public void wrapsInParentheses() {
    ExpressionChain chain = new ExpressionChain();
    ExpressionChain wrapped = OperatorBuilder.$(chain);

    Assert.assertNotSame(wrapped, chain);
    Assert.assertTrue(wrapped.getOperator() instanceof Parentheses);
  }

  @Test
  public void wrapsInNot() {
    ExpressionChain chain = new ExpressionChain();
    ExpressionChain wrapped = OperatorBuilder.not(chain);

    Assert.assertNotSame(wrapped, chain);
    Assert.assertTrue(wrapped.getOperator() instanceof Not);
  }

  @Test
  public void is() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").is("B").getOperator();

    Assert.assertTrue(operator instanceof Equal);
    Assert.assertEquals("A = B", toString(operator));
  }

  @Test
  public void operatorIs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).is("B").getOperator();

    Assert.assertTrue(operator instanceof Equal);
    Assert.assertEquals("dummy(A) = B", toString(operator));
  }

  @Test
  public void isNot() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").isNot("B").getOperator();

    Assert.assertTrue(operator instanceof NotEqual);
    Assert.assertEquals("A <> B", toString(operator));
  }

  @Test
  public void operatorIsNot() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).isNot("B").getOperator();

    Assert.assertTrue(operator instanceof NotEqual);
    Assert.assertEquals("dummy(A) <> B", toString(operator));
  }

  @Test
  public void isNull() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").isNull().getOperator();

    Assert.assertTrue(operator instanceof IsNull);
    Assert.assertEquals("A is null", toString(operator));
  }

  @Test
  public void operatorIsNull() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).isNull().getOperator();

    Assert.assertTrue(operator instanceof IsNull);
    Assert.assertEquals("dummy(A) is null", toString(operator));
  }

  @Test
  public void isNotNull() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").isNotNull().getOperator();

    Assert.assertTrue(operator instanceof IsNotNull);
    Assert.assertEquals("A is not null", toString(operator));
  }

  @Test
  public void operatorIsNotNull() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).isNotNull().getOperator();

    Assert.assertTrue(operator instanceof IsNotNull);
    Assert.assertEquals("dummy(A) is not null", toString(operator));
  }

  @Test
  public void between() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").between("B", "C").getOperator();

    Assert.assertTrue(operator instanceof Between);
    Assert.assertEquals("A between B and C", toString(operator));
  }

  @Test
  public void operatorBetween() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).between("B", "C").getOperator();

    Assert.assertTrue(operator instanceof Between);
    Assert.assertEquals("dummy(A) between B and C", toString(operator));
  }

  @Test
  public void notBetween() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notBetween("B", "C").getOperator();

    Assert.assertTrue(operator instanceof NotBetween);
    Assert.assertEquals("A not between B and C", toString(operator));
  }

  @Test
  public void operatorNotBetween() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).notBetween("B", "C").getOperator();

    Assert.assertTrue(operator instanceof NotBetween);
    Assert.assertEquals("dummy(A) not between B and C", toString(operator));
  }

  @Test
  public void in() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").in(Arrays.asList("B", "C")).getOperator();

    Assert.assertTrue(operator instanceof In);
    Assert.assertEquals("A in [B, C]", toString(operator));
  }

  @Test
  public void operatorIn() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).in(Arrays.asList("B", "C")).getOperator();

    Assert.assertTrue(operator instanceof In);
    Assert.assertEquals("dummy(A) in [B, C]", toString(operator));
  }

  @Test
  public void inVarargs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").in("B", "C").getOperator();

    Assert.assertTrue(operator instanceof In);
    Assert.assertEquals("A in [B, C]", toString(operator));
  }

  @Test
  public void operatorInVarargs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).in("B", "C").getOperator();

    Assert.assertTrue(operator instanceof In);
    Assert.assertEquals("dummy(A) in [B, C]", toString(operator));
  }

  @Test
  public void notIn() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notIn(Arrays.asList("B", "C")).getOperator();

    Assert.assertTrue(operator instanceof NotIn);
    Assert.assertEquals("A not in [B, C]", toString(operator));
  }

  @Test
  public void operatorNotIn() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).notIn(Arrays.asList("B", "C")).getOperator();

    Assert.assertTrue(operator instanceof NotIn);
    Assert.assertEquals("dummy(A) not in [B, C]", toString(operator));
  }

  @Test
  public void notInVarargs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").notIn("B", "C").getOperator();

    Assert.assertTrue(operator instanceof NotIn);
    Assert.assertEquals("A not in [B, C]", toString(operator));
  }

  @Test
  public void operatorNotInVarargs() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).notIn("B", "C").getOperator();

    Assert.assertTrue(operator instanceof NotIn);
    Assert.assertEquals("dummy(A) not in [B, C]", toString(operator));
  }

  @Test
  public void greaterThan() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").greaterThan("B").getOperator();

    Assert.assertTrue(operator instanceof GreaterThan);
    Assert.assertEquals("A > B", toString(operator));
  }

  @Test
  public void operatorGreaterThan() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).greaterThan("B").getOperator();

    Assert.assertTrue(operator instanceof GreaterThan);
    Assert.assertEquals("dummy(A) > B", toString(operator));
  }

  @Test
  public void greaterThanOrEqual() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").greaterThanOrEqual("B").getOperator();

    Assert.assertTrue(operator instanceof GreaterThanOrEqual);
    Assert.assertEquals("A >= B", toString(operator));
  }

  @Test
  public void operatorGreaterThanOrEqual() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).greaterThanOrEqual("B").getOperator();

    Assert.assertTrue(operator instanceof GreaterThanOrEqual);
    Assert.assertEquals("dummy(A) >= B", toString(operator));
  }

  @Test
  public void lessThan() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").lessThan("B").getOperator();

    Assert.assertTrue(operator instanceof LessThan);
    Assert.assertEquals("A < B", toString(operator));
  }

  @Test
  public void operatorLessThan() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).lessThan("B").getOperator();

    Assert.assertTrue(operator instanceof LessThan);
    Assert.assertEquals("dummy(A) < B", toString(operator));
  }

  @Test
  public void lessThanOrEqual() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, "A").lessThanOrEqual("B").getOperator();

    Assert.assertTrue(operator instanceof LessThanOrEqual);
    Assert.assertEquals("A <= B", toString(operator));
  }

  @Test
  public void operatorLessThanOrEqual() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new OperatorBuilder<>(chain, new Dummy<>("A")).lessThanOrEqual("B").getOperator();

    Assert.assertTrue(operator instanceof LessThanOrEqual);
    Assert.assertEquals("dummy(A) <= B", toString(operator));
  }

  private String toString(Operator operator) {
    JpqlStringWriter stringWriter = new DummyJpqlStringWriter();
    operator.writeTo(stringWriter);
    return stringWriter.toString();
  }

  private static class Dummy<T> extends UnaryOperator<T> {
    private Dummy(T operand) {
      super(operand);
    }

    @Override
    public void writeTo(JpqlStringWriter stringWriter) {
      stringWriter.appendString("dummy(");
      writeOperand(operand, stringWriter);
      stringWriter.appendString(")");
    }
  }
}
