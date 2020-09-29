package org.thepavel.jpqlbuilder.operators.builders;

import org.thepavel.jpqlbuilder.operators.Operator;

public class ExpressionChain extends BaseExpressionChain<ExpressionChain> {
  ExpressionChain() {
    super();
  }

  ExpressionChain(Operator operator) {
    super(operator);
  }
}
