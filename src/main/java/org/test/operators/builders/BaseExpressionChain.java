package org.test.operators.builders;

import org.test.functions.JpqlFunction;
import org.test.operators.And;
import org.test.operators.Operator;
import org.test.operators.Or;
import org.test.operators.Parentheses;

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
