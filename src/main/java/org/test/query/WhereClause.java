package org.test.query;

import org.test.querystring.JpqlStringWriter;
import org.test.operators.Operator;

public class WhereClause implements Operator {
  private Operator operator;

  public void setOperator(Operator operator) {
    this.operator = operator;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    if (operator == null) {
      return;
    }
    stringWriter.appendString(" where ");
    writeOperand(operator, stringWriter);
  }
}
