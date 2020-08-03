package org.test.operators.builders;

import org.test.operators.Like;

public class StringOperatorBuilder extends OperatorBuilder<String> {
  StringOperatorBuilder(String operand) {
    super(operand);
  }

  StringOperatorBuilder(ExpressionChain chain, String operand) {
    super(chain, operand);
  }

  public static StringOperatorBuilder $(String operand) {
    return new StringOperatorBuilder(operand);
  }

  public ExpressionChain like(String value) {
    return chain.join(new Like(operand, value));
  }
}
