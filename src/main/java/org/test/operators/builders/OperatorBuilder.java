package org.test.operators.builders;

import org.test.operators.Equal;
import org.test.operators.IsNotNull;
import org.test.operators.IsNull;
import org.test.operators.Not;
import org.test.operators.NotEqual;

public class OperatorBuilder<T> {
  final ExpressionChain chain;
  final T operand;

  OperatorBuilder(T operand) {
    this(new ExpressionChain(), operand);
  }

  OperatorBuilder(ExpressionChain chain, T operand) {
    this.chain = chain;
    this.operand = operand;
  }

  public static <E> OperatorBuilder<E> $(E operand) {
    return new OperatorBuilder<>(operand);
  }

  public static ExpressionChain not(ExpressionChain chain) {
    return new ExpressionChain(new Not(chain.getOperator()));
  }

  public ExpressionChain is(T value) {
    return chain.join(new Equal<>(operand, value));
  }

  public ExpressionChain isNot(T value) {
    return chain.join(new NotEqual<>(operand, value));
  }

  public ExpressionChain isNull() {
    return chain.join(new IsNull<>(operand));
  }

  public ExpressionChain isNotNull() {
    return chain.join(new IsNotNull<>(operand));
  }
}
