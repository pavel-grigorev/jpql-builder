package org.test.query;

import org.test.JpqlStringBuilder;
import org.test.operators.Operator;
import org.test.operators.builders.ExpressionChain;

public class JoinClause extends Operator {
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
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    stringBuilder.appendString(type.getClause());
    stringBuilder.appendValue(joinedThing);
    appendAlias(stringBuilder);
    appendOnClause(stringBuilder);
  }

  private void appendAlias(JpqlStringBuilder<?> stringBuilder) {
    if (type.hasAlias()) {
      stringBuilder.appendString(" ");
      stringBuilder.appendString(alias);
    }
  }

  private void appendOnClause(JpqlStringBuilder<?> stringBuilder) {
    if (onClause != null) {
      stringBuilder.appendString(" on ");
      writeOperand(onClause, stringBuilder);
    }
  }
}
