package org.test;

import org.test.operators.builders.ExpressionChain;
import org.test.path.PathResolver;
import org.test.query.JoinClause;

import java.util.function.Function;

public class Join<T> {
  private final JoinClause joinClause;
  private final PathResolver<T> pathResolver;

  Join(JoinClause joinClause, PathResolver<T> pathResolver) {
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
