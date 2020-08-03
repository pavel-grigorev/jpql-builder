package org.test;

import org.test.operators.Like;

public class JpqlBuilderStringOperatorBuilder<T> extends JpqlBuilderOperatorBuilder<String, T> {
  JpqlBuilderStringOperatorBuilder(JpqlBuilder<T> builder, String operand) {
    super(builder, operand);
  }

  public JpqlBuilder<T> like(String value) {
    new Like(operand, value).writeTo(builder);
    return builder;
  }
}
