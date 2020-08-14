package org.test;

import org.test.operators.builders.ExpressionChain;
import org.test.query.JoinClause;

import java.util.function.Function;

public class JpqlBuilderJoinChain<T> {
  private final JoinClause joinClause;
  private final PathResolver<T> pathResolver;

  JpqlBuilderJoinChain(JoinClause joinClause, PathResolver<T> pathResolver) {
    this.joinClause = joinClause;
    this.pathResolver = pathResolver;
  }

  public T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }

  public On on(Function<T, ExpressionChain> chainFunction) {
    joinClause.setOnClause(chainFunction.apply(getPathSpecifier()));
    return new On();
  }

  public class On {
    public T getPathSpecifier() {
      return pathResolver.getPathSpecifier();
    }
  }
}
