package org.thepavel.jpqlbuilder.query;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.thepavel.jpqlbuilder.operators.Operator;

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
