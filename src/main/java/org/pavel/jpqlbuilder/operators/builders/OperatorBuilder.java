package org.pavel.jpqlbuilder.operators.builders;

import org.pavel.jpqlbuilder.functions.JpqlFunction;
import org.pavel.jpqlbuilder.operators.Between;
import org.pavel.jpqlbuilder.operators.Equal;
import org.pavel.jpqlbuilder.operators.GreaterThan;
import org.pavel.jpqlbuilder.operators.GreaterThanOrEqual;
import org.pavel.jpqlbuilder.operators.In;
import org.pavel.jpqlbuilder.operators.IsNotNull;
import org.pavel.jpqlbuilder.operators.IsNull;
import org.pavel.jpqlbuilder.operators.LessThan;
import org.pavel.jpqlbuilder.operators.LessThanOrEqual;
import org.pavel.jpqlbuilder.operators.Like;
import org.pavel.jpqlbuilder.operators.MemberOf;
import org.pavel.jpqlbuilder.operators.Not;
import org.pavel.jpqlbuilder.operators.NotBetween;
import org.pavel.jpqlbuilder.operators.NotEqual;
import org.pavel.jpqlbuilder.operators.NotIn;
import org.pavel.jpqlbuilder.operators.NotLike;
import org.pavel.jpqlbuilder.operators.NotMemberOf;
import org.pavel.jpqlbuilder.operators.Parentheses;

import java.util.Arrays;
import java.util.Collection;

public class OperatorBuilder<T, B extends BaseExpressionChain<B>> {
  final B chain;
  final Object operand;

  public OperatorBuilder(B chain, T operand) {
    this.chain = chain;
    this.operand = operand;
  }

  public OperatorBuilder(B chain, JpqlFunction<T> operator) {
    this.chain = chain;
    this.operand = operator;
  }

  public static <T> OperatorBuilder<T, ExpressionChain> $(T operand) {
    return new OperatorBuilder<>(new ExpressionChain(), operand);
  }

  public static <T> OperatorBuilder<T, ExpressionChain> $(JpqlFunction<T> operator) {
    return new OperatorBuilder<>(new ExpressionChain(), operator);
  }

  public static CollectionOperatorBuilder<ExpressionChain> $(Collection<?> operand) {
    return new CollectionOperatorBuilder<>(new ExpressionChain(), operand);
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

  public B is(JpqlFunction<T> operator) {
    return chain.join(new Equal<>(operand, operator));
  }

  public B isNot(T value) {
    return chain.join(new NotEqual<>(operand, value));
  }

  public B isNot(JpqlFunction<T> operator) {
    return chain.join(new NotEqual<>(operand, operator));
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

  public B between(JpqlFunction<T> min, T max) {
    return chain.join(new Between<>(operand, min, max));
  }

  public B between(T min, JpqlFunction<T> max) {
    return chain.join(new Between<>(operand, min, max));
  }

  public B between(JpqlFunction<T> min, JpqlFunction<T> max) {
    return chain.join(new Between<>(operand, min, max));
  }

  public B notBetween(T min, T max) {
    return chain.join(new NotBetween<>(operand, min, max));
  }

  public B notBetween(JpqlFunction<T> min, T max) {
    return chain.join(new NotBetween<>(operand, min, max));
  }

  public B notBetween(T min, JpqlFunction<T> max) {
    return chain.join(new NotBetween<>(operand, min, max));
  }

  public B notBetween(JpqlFunction<T> min, JpqlFunction<T> max) {
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

  public B greaterThan(JpqlFunction<T> operator) {
    return chain.join(new GreaterThan<>(operand, operator));
  }

  public B greaterThanOrEqual(T value) {
    return chain.join(new GreaterThanOrEqual<>(operand, value));
  }

  public B greaterThanOrEqual(JpqlFunction<T> operator) {
    return chain.join(new GreaterThanOrEqual<>(operand, operator));
  }

  public B lessThan(T value) {
    return chain.join(new LessThan<>(operand, value));
  }

  public B lessThan(JpqlFunction<T> operator) {
    return chain.join(new LessThan<>(operand, operator));
  }

  public B lessThanOrEqual(T value) {
    return chain.join(new LessThanOrEqual<>(operand, value));
  }

  public B lessThanOrEqual(JpqlFunction<T> operator) {
    return chain.join(new LessThanOrEqual<>(operand, operator));
  }

  public B like(T value) {
    return chain.join(new Like(operand, value));
  }

  public B like(JpqlFunction<T> operator) {
    return chain.join(new Like(operand, operator));
  }

  public B like(T value, String escapeChar) {
    return chain.join(new Like(operand, value, escapeChar));
  }

  public B like(JpqlFunction<T> operator, String escapeChar) {
    return chain.join(new Like(operand, operator, escapeChar));
  }

  public B notLike(T value) {
    return chain.join(new NotLike(operand, value));
  }

  public B notLike(JpqlFunction<T> operator) {
    return chain.join(new NotLike(operand, operator));
  }

  public B notLike(T value, String escapeChar) {
    return chain.join(new NotLike(operand, value, escapeChar));
  }

  public B notLike(JpqlFunction<T> operator, String escapeChar) {
    return chain.join(new NotLike(operand, operator, escapeChar));
  }

  public B memberOf(Collection<T> collection) {
    return chain.join(new MemberOf<>(operand, collection));
  }

  public B notMemberOf(Collection<T> collection) {
    return chain.join(new NotMemberOf<>(operand, collection));
  }
}
