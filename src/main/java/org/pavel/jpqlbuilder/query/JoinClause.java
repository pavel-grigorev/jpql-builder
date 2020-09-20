package org.pavel.jpqlbuilder.query;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.pavel.jpqlbuilder.operators.Operator;
import org.pavel.jpqlbuilder.operators.builders.ExpressionChain;

public class JoinClause implements Operator {
  private final String alias;
  private final Object joinedThing;
  private final JoinType type;
  private Operator onClause;
  private Class<?> treatAsType;

  public JoinClause(String alias, Object joinedThing, JoinType type) {
    this.alias = alias;
    this.joinedThing = joinedThing;
    this.type = type;
  }

  public void setOnClause(ExpressionChain chain) {
    onClause = chain.getOperator();
  }

  public void setTreatAsType(Class<?> treatAsType) {
    this.treatAsType = treatAsType;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString(type.getClause());
    appendJoinedThing(stringWriter);
    appendAlias(stringWriter);
    appendOnClause(stringWriter);
  }

  private void appendJoinedThing(JpqlStringWriter stringWriter) {
    if (treatAsType != null) {
      appendTreatAsType(stringWriter);
    } else {
      writeOperand(joinedThing, stringWriter);
    }
  }

  private void appendTreatAsType(JpqlStringWriter stringWriter) {
    stringWriter.appendString("treat(");
    writeOperand(joinedThing, stringWriter);
    stringWriter.appendString(" as ");
    writeOperand(treatAsType, stringWriter);
    stringWriter.appendString(")");
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
