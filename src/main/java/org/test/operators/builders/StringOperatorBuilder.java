package org.test.operators.builders;

import org.test.operators.Like;
import org.test.operators.NotLike;
import org.test.operators.UnaryOperator;

public class StringOperatorBuilder<B extends BaseExpressionChain<B>> extends OperatorBuilder<String, B> {
  public StringOperatorBuilder(B chain, String operand) {
    super(chain, operand);
  }

  public StringOperatorBuilder(B chain, UnaryOperator<String> operator) {
    super(chain, operator);
  }

  public static StringOperatorBuilder<ExpressionChain> $(String operand) {
    return new StringOperatorBuilder<>(new ExpressionChain(), operand);
  }

  public static StringOperatorBuilder<ExpressionChain> $(UnaryOperator<String> operator) {
    return new StringOperatorBuilder<>(new ExpressionChain(), operator);
  }

  public B like(String value) {
    return chain.join(new Like(operand, value));
  }

  public B like(UnaryOperator<String> operator) {
    return chain.join(new Like(operand, operator));
  }

  public B like(String value, String escapeChar) {
    return chain.join(new Like(operand, value, escapeChar));
  }

  public B like(UnaryOperator<String> operator, String escapeChar) {
    return chain.join(new Like(operand, operator, escapeChar));
  }

  public B notLike(String value) {
    return chain.join(new NotLike(operand, value));
  }

  public B notLike(UnaryOperator<String> operator) {
    return chain.join(new NotLike(operand, operator));
  }

  public B notLike(String value, String escapeChar) {
    return chain.join(new NotLike(operand, value, escapeChar));
  }

  public B notLike(UnaryOperator<String> operator, String escapeChar) {
    return chain.join(new NotLike(operand, operator, escapeChar));
  }
}
