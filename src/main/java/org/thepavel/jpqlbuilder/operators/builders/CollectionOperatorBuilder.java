package org.thepavel.jpqlbuilder.operators.builders;

import org.thepavel.jpqlbuilder.operators.IsEmpty;
import org.thepavel.jpqlbuilder.operators.IsNotEmpty;

import java.util.Collection;

public class CollectionOperatorBuilder<B extends BaseExpressionChain<B>> {
  final B chain;
  final Collection<?> operand;

  public CollectionOperatorBuilder(B chain, Collection<?> operand) {
    this.chain = chain;
    this.operand = operand;
  }

  public B isEmpty() {
    return chain.join(new IsEmpty(operand));
  }

  public B isNotEmpty() {
    return chain.join(new IsNotEmpty(operand));
  }
}
