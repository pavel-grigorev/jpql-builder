package org.test.operators.builders;

import org.test.operators.Operator;

public class ExpressionChain extends BaseExpressionChain<ExpressionChain> {
  ExpressionChain() {
    super();
  }

  ExpressionChain(Operator operator) {
    super(operator);
  }

  @Override
  protected ExpressionChain createChain(Operator operator) {
    return new ExpressionChain(operator);
  }
}
