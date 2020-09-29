/*
 * Copyright (c) 2020 Pavel Grigorev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thepavel.jpqlbuilder.query;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.thepavel.jpqlbuilder.operators.Operator;
import org.thepavel.jpqlbuilder.operators.builders.ExpressionChain;

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
