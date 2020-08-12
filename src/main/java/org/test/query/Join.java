package org.test.query;

import org.test.JpqlStringBuilder;
import org.test.operators.Operator;

public class Join extends Operator {
  private final String alias;
  private final Object joinedThing;
  private final JoinType type;
  private Operator onClause;

  public Join(String alias, Object joinedThing, JoinType type) {
    this.alias = alias;
    this.joinedThing = joinedThing;
    this.type = type;
  }

  public void setOnClause(Operator onClause) {
    this.onClause = onClause;
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
