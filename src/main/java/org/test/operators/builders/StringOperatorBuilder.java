package org.test.operators.builders;

import org.test.operators.Like;
import org.test.operators.NotLike;

public class StringOperatorBuilder<B extends BaseExpressionChain<B>> extends OperatorBuilder<String, B> {
  public StringOperatorBuilder(B chain, String operand) {
    super(chain, operand);
  }

  public static StringOperatorBuilder<ExpressionChain> $(String operand) {
    return new StringOperatorBuilder<>(new ExpressionChain(), operand);
  }

  public B like(String value) {
    return chain.join(new Like(operand, value));
  }

  public B like(String value, String escapeChar) {
    return chain.join(new Like(operand, value, escapeChar));
  }

  public B notLike(String value) {
    return chain.join(new NotLike(operand, value));
  }

  public B notLike(String value, String escapeChar) {
    return chain.join(new NotLike(operand, value, escapeChar));
  }
}
