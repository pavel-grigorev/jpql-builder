package org.test.operators.builders;

import org.test.operators.Like;

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
}
