package org.test.operators;

import org.test.JpqlStringBuilder;

import java.util.Collection;

public class OrderBy<T> extends UnaryOperator<Collection<T>> {
  private String nullsOrder;

  public OrderBy(Collection<T> operand) {
    super(operand);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    stringBuilder.appendString(" order by ");

    int i = 0;
    for (T value : operand) {
      if (i > 0) {
        stringBuilder.appendString(", ");
      }
      writeOperand(value, stringBuilder);
      i++;
    }

    if (nullsOrder != null) {
      stringBuilder.appendString(nullsOrder);
    }
  }

  public void setNullsFirst() {
    nullsOrder = " nulls first";
  }

  public void setNullsLast() {
    nullsOrder = " nulls last";
  }
}
