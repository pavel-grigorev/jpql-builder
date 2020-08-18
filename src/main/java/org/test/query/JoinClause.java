package org.test.query;

import org.test.querystring.JpqlStringWriter;
import org.test.operators.Operator;
import org.test.operators.builders.ExpressionChain;

public class JoinClause implements Operator {
  private final String alias;
  private final Object joinedThing;
  private final JoinType type;
  private Operator onClause;

  public JoinClause(String alias, Object joinedThing, JoinType type) {
    this.alias = alias;
    this.joinedThing = joinedThing;
    this.type = type;
  }

  public void setOnClause(ExpressionChain chain) {
    onClause = chain.getOperator();
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString(type.getClause());
    stringWriter.appendValue(joinedThing);
    appendAlias(stringWriter);
    appendOnClause(stringWriter);
  }

  private void appendAlias(JpqlStringWriter stringWriter) {
    if (type.hasAlias()) {
      stringWriter.appendString(" ");
      stringWriter.appendString(alias);
    }
  }

  private void appendOnClause(JpqlStringWriter stringWriter) {
    if (onClause != null) {
      stringWriter.appendString(" on ");
      writeOperand(onClause, stringWriter);
    }
  }
}
