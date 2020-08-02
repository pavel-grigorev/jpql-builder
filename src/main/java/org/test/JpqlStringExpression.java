package org.test;

import org.test.operators.Like;

public class JpqlStringExpression<T> extends JpqlExpression<String, T> {
  JpqlStringExpression(JpqlBuilder<T> builder, String operand) {
    super(builder, operand);
  }

  public JpqlBuilder<T> like(String value) {
    new Like(operand, value).writeTo(builder);
    return builder;
  }
}
