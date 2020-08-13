package org.test;

import org.test.operators.builders.ExpressionChain;
import org.test.query.Join;
import org.test.query.JoinType;

import java.util.function.Function;

public class JpqlBuilderJoinChain<T> {
  private final Join joinClause;
  private final PathResolver<T> pathResolver;
  private ExpressionChain onChain;

  JpqlBuilderJoinChain(String alias, Object joinedThing, JoinType type, PathResolver<T> pathResolver) {
    this.joinClause = new Join(alias, joinedThing, type);
    this.pathResolver = pathResolver;
  }

  public T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }

  public On on(Function<T, ExpressionChain> chainFunction) {
    onChain = chainFunction.apply(getPathSpecifier());
    return new On();
  }

  void writeTo(JpqlStringBuilder<?> stringBuilder) {
    if (onChain != null) {
      joinClause.setOnClause(onChain.getOperator());
    }
    joinClause.writeTo(stringBuilder);
  }

  public class On {
    public T getPathSpecifier() {
      return pathResolver.getPathSpecifier();
    }
  }
}
