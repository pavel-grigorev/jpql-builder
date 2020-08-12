package org.test.operators.builders;

import org.test.operators.Between;
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
import org.test.operators.Parentheses;
import org.test.operators.UnaryOperator;

import java.util.Arrays;
import java.util.Collection;

public class OperatorBuilder<T, B extends BaseExpressionChain<B>> {
  final B chain;
  final Object operand;

  public OperatorBuilder(B chain, T operand) {
    this.chain = chain;
    this.operand = operand;
  }

  public OperatorBuilder(B chain, UnaryOperator<T> operator) {
    this.chain = chain;
    this.operand = operator;
  }

  public static <T> OperatorBuilder<T, ExpressionChain> $(T operand) {
    return new OperatorBuilder<>(new ExpressionChain(), operand);
  }

  public static ExpressionChain $(ExpressionChain chain) {
    return new ExpressionChain(new Parentheses(chain.getOperator()));
  }

  public static ExpressionChain not(ExpressionChain chain) {
    return new ExpressionChain(new Not(chain.getOperator()));
  }

  public B is(T value) {
    return chain.join(new Equal<>(operand, value));
  }

  public B isNot(T value) {
    return chain.join(new NotEqual<>(operand, value));
  }

  public B isNull() {
    return chain.join(new IsNull<>(operand));
  }

  public B isNotNull() {
    return chain.join(new IsNotNull<>(operand));
  }

  public B between(T min, T max) {
    return chain.join(new Between<>(operand, min, max));
  }

  public B notBetween(T min, T max) {
    return chain.join(new NotBetween<>(operand, min, max));
  }

  public B in(Collection<T> values) {
    return chain.join(new In<>(operand, values));
  }

  @SafeVarargs
  public final B in(T... values) {
    return in(Arrays.asList(values));
  }

  public B notIn(Collection<T> values) {
    return chain.join(new NotIn<>(operand, values));
  }

  @SafeVarargs
  public final B notIn(T... values) {
    return notIn(Arrays.asList(values));
  }

  public B greaterThan(T value) {
    return chain.join(new GreaterThan<>(operand, value));
  }

  public B greaterThanOrEqual(T value) {
    return chain.join(new GreaterThanOrEqual<>(operand, value));
  }

  public B lessThan(T value) {
    return chain.join(new LessThan<>(operand, value));
  }

  public B lessThanOrEqual(T value) {
    return chain.join(new LessThanOrEqual<>(operand, value));
  }
}
