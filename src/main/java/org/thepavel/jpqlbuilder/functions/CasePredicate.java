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

package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.operators.Operator;
import org.thepavel.jpqlbuilder.operators.Parentheses;
import org.thepavel.jpqlbuilder.operators.builders.BaseExpressionChain;
import org.thepavel.jpqlbuilder.operators.builders.CollectionOperatorBuilder;
import org.thepavel.jpqlbuilder.operators.builders.ExpressionChain;
import org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder;
import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CasePredicate {
  public <P> OperatorBuilder<P, ExpressionBuilder> when(P operand) {
    return new OperatorBuilder<>(new ExpressionBuilder(), operand);
  }

  public <P> OperatorBuilder<P, ExpressionBuilder> when(JpqlFunction<P> operator) {
    return new OperatorBuilder<>(new ExpressionBuilder(), operator);
  }

  public CollectionOperatorBuilder<ExpressionBuilder> when(Collection<?> operand) {
    return new CollectionOperatorBuilder<>(new ExpressionBuilder(), operand);
  }

  public ExpressionBuilder when(ExpressionChain chain) {
    return new ExpressionBuilder(new Parentheses(chain.getOperator()));
  }

  public static class ExpressionBuilder extends BaseExpressionChain<ExpressionBuilder> {
    private ExpressionBuilder() {
    }

    private ExpressionBuilder(Operator operator) {
      super(operator);
    }

    public <T> Expression<T> then(T thenExpression) {
      return new Expression<>(this, thenExpression);
    }

    public <T> Expression<T> then(JpqlFunction<T> thenExpression) {
      return new Expression<>(this, thenExpression);
    }
  }

  public static class Expression<T> extends JpqlFunction<T> {
    private final List<WhenClause> whenClauses = new ArrayList<>();
    private Object orElse;

    private Expression(ExpressionBuilder builder, T thenExpression) {
      whenClauses.add(new WhenClause(builder.getOperator(), thenExpression));
    }

    private Expression(ExpressionBuilder builder, JpqlFunction<T> thenExpression) {
      whenClauses.add(new WhenClause(builder.getOperator(), thenExpression));
    }

    public <P> OperatorBuilder<P, WhereExpression<T>> when(P operand) {
      return new OperatorBuilder<>(new WhereExpression<>(this), operand);
    }

    public <P> OperatorBuilder<P, WhereExpression<T>> when(JpqlFunction<P> operator) {
      return new OperatorBuilder<>(new WhereExpression<>(this), operator);
    }

    public CollectionOperatorBuilder<WhereExpression<T>> when(Collection<?> operand) {
      return new CollectionOperatorBuilder<>(new WhereExpression<>(this), operand);
    }

    public WhereExpression<T> when(ExpressionChain chain) {
      return new WhereExpression<>(new Parentheses(chain.getOperator()), this);
    }

    public Expression<T> orElse(T elseExpression) {
      orElse = elseExpression;
      return this;
    }

    public Expression<T> orElse(JpqlFunction<T> elseExpression) {
      orElse = elseExpression;
      return this;
    }

    @Override
    public void writeTo(JpqlStringWriter stringWriter) {
      stringWriter.appendString("case");

      for (WhenClause whenClause : whenClauses) {
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

  public static class WhereExpression<T> extends BaseExpressionChain<WhereExpression<T>> {
    private final Expression<T> expression;

    private WhereExpression(Expression<T> expression) {
      this.expression = expression;
    }

    private WhereExpression(Operator operator, Expression<T> expression) {
      super(operator);
      this.expression = expression;
    }

    public Expression<T> then(T thenExpression) {
      return addThen(thenExpression);
    }

    public Expression<T> then(JpqlFunction<T> thenExpression) {
      return addThen(thenExpression);
    }

    private Expression<T> addThen(Object thenExpression) {
      expression.whenClauses.add(new WhenClause(getOperator(), thenExpression));
      return expression;
    }
  }

  private static class WhenClause {
    private Operator whenExpression;
    private Object thenExpression;

    private WhenClause(Operator whenExpression, Object thenExpression) {
      this.whenExpression = whenExpression;
      this.thenExpression = thenExpression;
    }
  }
}
