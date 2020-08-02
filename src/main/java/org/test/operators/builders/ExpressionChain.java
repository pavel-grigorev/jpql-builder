package org.test.operators.builders;

import org.test.operators.And;
import org.test.operators.Operator;
import org.test.operators.Or;
import org.test.operators.Parentheses;

import java.util.function.BinaryOperator;

public class ExpressionChain {
  private final Operator operator;
  private BinaryOperator<Operator> joiner;

  ExpressionChain(Operator operator) {
    this.operator = operator;
  }

  public Operator getOperator() {
    return operator;
  }

  public <T> OperatorBuilder<T> and(T operand) {
    joiner = And::new;
    return new OperatorBuilder<>(this, operand);
  }

  public StringOperatorBuilder and(String operand) {
    joiner = And::new;
    return new StringOperatorBuilder(this, operand);
  }

  public ExpressionChain and(ExpressionChain chain) {
    joiner = And::new;
    return join(chain);
  }

  public <T> OperatorBuilder<T> or(T operand) {
    joiner = Or::new;
    return new OperatorBuilder<>(this, operand);
  }

  public StringOperatorBuilder or(String operand) {
    joiner = Or::new;
    return new StringOperatorBuilder(this, operand);
  }

  public ExpressionChain or(ExpressionChain chain) {
    joiner = Or::new;
    return join(chain);
  }

  ExpressionChain join(Operator operator) {
    return new ExpressionChain(joiner.apply(this.operator, operator));
  }

  private ExpressionChain join(ExpressionChain chain) {
    return join(new Parentheses(chain.getOperator()));
  }
}
