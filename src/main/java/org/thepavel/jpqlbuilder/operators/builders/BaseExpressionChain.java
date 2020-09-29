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

package org.thepavel.jpqlbuilder.operators.builders;

import org.thepavel.jpqlbuilder.functions.JpqlFunction;
import org.thepavel.jpqlbuilder.operators.And;
import org.thepavel.jpqlbuilder.operators.Operator;
import org.thepavel.jpqlbuilder.operators.Or;
import org.thepavel.jpqlbuilder.operators.Parentheses;

import java.util.Collection;
import java.util.function.BinaryOperator;

public abstract class BaseExpressionChain<B extends BaseExpressionChain<B>> {
  private Operator operator;
  private BinaryOperator<Operator> joiner;

  protected BaseExpressionChain() {
    this(null);
  }

  protected BaseExpressionChain(Operator operator) {
    this.operator = operator;
  }

  public Operator getOperator() {
    return operator;
  }

  public <T> OperatorBuilder<T, B> and(T operand) {
    joiner = And::new;
    return new OperatorBuilder<>(thisChain(), operand);
  }

  public <T> OperatorBuilder<T, B> and(JpqlFunction<T> operator) {
    joiner = And::new;
    return new OperatorBuilder<>(thisChain(), operator);
  }

  public CollectionOperatorBuilder<B> and(Collection<?> operand) {
    joiner = And::new;
    return new CollectionOperatorBuilder<>(thisChain(), operand);
  }

  public B and(ExpressionChain chain) {
    joiner = And::new;
    return join(chain);
  }

  public <T> OperatorBuilder<T, B> or(T operand) {
    joiner = Or::new;
    return new OperatorBuilder<>(thisChain(), operand);
  }

  public <T> OperatorBuilder<T, B> or(JpqlFunction<T> operator) {
    joiner = Or::new;
    return new OperatorBuilder<>(thisChain(), operator);
  }

  public CollectionOperatorBuilder<B> or(Collection<?> operand) {
    joiner = Or::new;
    return new CollectionOperatorBuilder<>(thisChain(), operand);
  }

  public B or(ExpressionChain chain) {
    joiner = Or::new;
    return join(chain);
  }

  @SuppressWarnings("unchecked")
  private B thisChain() {
    return (B) this;
  }

  private B join(ExpressionChain chain) {
    return join(new Parentheses(chain.getOperator()));
  }

  B join(Operator operator) {
    if (this.operator == null) {
      setOperator(operator);
    } else {
      setOperator(joiner.apply(this.operator, operator));
    }
    return thisChain();
  }

  private void setOperator(Operator operator) {
    this.operator = operator;
    onOperatorChange(operator);
  }

  protected void onOperatorChange(Operator operator) {
  }
}
