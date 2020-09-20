package org.pavel.jpqlbuilder.operators.builders;

import org.pavel.jpqlbuilder.operators.Operator;

public class ExpressionChain extends BaseExpressionChain<ExpressionChain> {
  ExpressionChain() {
    super();
  }

  ExpressionChain(Operator operator) {
    super(operator);
  }
}
