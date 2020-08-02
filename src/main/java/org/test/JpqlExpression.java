package org.test;

import org.test.operators.Equal;
import org.test.operators.IsNotNull;
import org.test.operators.IsNull;
import org.test.operators.NotEqual;

// TODO: rename to JpqlBuilderExpression
// TODO: extend ExpressionChain?
public class JpqlExpression<P, T> {
  final JpqlBuilder<T> builder;
  final P operand;

  JpqlExpression(JpqlBuilder<T> builder, P operand) {
    this.builder = builder;
    this.operand = operand;
  }

  public JpqlBuilder<T> is(P value) {
    new Equal<>(operand, value).writeTo(builder);
    return builder;
  }

  public JpqlBuilder<T> isNot(P value) {
    new NotEqual<>(operand, value).writeTo(builder);
    return builder;
  }

  public JpqlBuilder<T> isNull() {
    new IsNull<>(operand).writeTo(builder);
    return builder;
  }

  public JpqlBuilder<T> isNotNull() {
    new IsNotNull<>(operand).writeTo(builder);
    return builder;
  }
}
