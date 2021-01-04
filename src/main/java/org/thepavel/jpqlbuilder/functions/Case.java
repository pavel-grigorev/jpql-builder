/*
 * Copyright (c) 2020-2021 Pavel Grigorev.
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

package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.ArrayList;
import java.util.List;

public class Case<P> {
  private final Object argument;

  public Case(P argument) {
    this.argument = argument;
  }

  public Case(JpqlFunction<P> argument) {
    this.argument = argument;
  }

  public ExpressionBuilder<P> when(P whenExpression) {
    return new ExpressionBuilder<>(this, whenExpression);
  }

  public ExpressionBuilder<P> when(JpqlFunction<P> whenExpression) {
    return new ExpressionBuilder<>(this, whenExpression);
  }

  public static class ExpressionBuilder<P> {
    private final Object argument;
    private final Object whenExpression;

    private ExpressionBuilder(Case<P> parent, P whenExpression) {
      this.argument = parent.argument;
      this.whenExpression = whenExpression;
    }

    private ExpressionBuilder(Case<P> parent, JpqlFunction<P> whenExpression) {
      this.argument = parent.argument;
      this.whenExpression = whenExpression;
    }

    public <T> Expression<P, T> then(T thenExpression) {
      return new Expression<>(this, thenExpression);
    }

    public <T> Expression<P, T> then(JpqlFunction<T> thenExpression) {
      return new Expression<>(this, thenExpression);
    }
  }

  public static class Expression<P, T> extends JpqlFunction<T> {
    private final List<WhenClause<P, T>> whenClauses = new ArrayList<>();
    private final Object argument;
    private Object orElse;

    private Expression(ExpressionBuilder<P> builder, T thenExpression) {
      argument = builder.argument;
      whenClauses.add(new WhenClause<>(builder.whenExpression, thenExpression));
    }

    private Expression(ExpressionBuilder<P> builder, JpqlFunction<T> thenExpression) {
      argument = builder.argument;
      whenClauses.add(new WhenClause<>(builder.whenExpression, thenExpression));
    }

    public WhenClause<P, T> when(P whenExpression) {
      return new WhenClause<>(this, whenExpression);
    }

    public WhenClause<P, T> when(JpqlFunction<P> whenExpression) {
      return new WhenClause<>(this, whenExpression);
    }

    public Expression<P, T> orElse(T elseExpression) {
      orElse = elseExpression;
      return this;
    }

    public Expression<P, T> orElse(JpqlFunction<T> elseExpression) {
      orElse = elseExpression;
      return this;
    }

    @Override
    public void writeTo(JpqlStringWriter stringWriter) {
      stringWriter.appendString("case ");
      writeOperand(argument, stringWriter);

      for (WhenClause<P, T> whenClause : whenClauses) {
        stringWriter.appendString(" when ");
        writeOperand(whenClause.whenExpression, stringWriter);
        stringWriter.appendString(" then ");
        writeOperand(whenClause.thenExpression, stringWriter);
      }

      if (orElse != null) {
        stringWriter.appendString(" else ");
        writeOperand(orElse, stringWriter);
      }

      stringWriter.appendString(" end");
    }
  }

  public static class WhenClause<P, T> {
    private Expression<P, T> expression;
    private Object whenExpression;
    private Object thenExpression;

    private WhenClause(Object whenExpression, Object thenExpression) {
      this.whenExpression = whenExpression;
      this.thenExpression = thenExpression;
    }

    private WhenClause(Expression<P, T> expression, Object whenExpression) {
      this.expression = expression;
      this.whenExpression = whenExpression;
    }

    public Expression<P, T> then(T thenExpression) {
      return addThen(thenExpression);
    }

    public Expression<P, T> then(JpqlFunction<T> thenExpression) {
      return addThen(thenExpression);
    }

    private Expression<P, T> addThen(Object thenExpression) {
      this.thenExpression = thenExpression;
      expression.whenClauses.add(this);
      return expression;
    }
  }
}
