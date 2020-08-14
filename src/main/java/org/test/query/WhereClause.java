package org.test.query;

import org.test.JpqlStringBuilder;
import org.test.operators.Operator;

public class WhereClause extends Operator {
  private Operator operator;

  public void setOperator(Operator operator) {
    this.operator = operator;
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    if (operator == null) {
      return;
    }
    stringBuilder.appendString(" where ");
    writeOperand(operator, stringBuilder);
  }
}
