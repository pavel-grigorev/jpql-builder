package org.pavel.jpqlbuilder;

import org.pavel.jpqlbuilder.operators.builders.ExpressionChain;
import org.pavel.jpqlbuilder.path.PathResolver;
import org.pavel.jpqlbuilder.query.JoinClause;
import org.pavel.jpqlbuilder.utils.EntityHelper;

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

  @SuppressWarnings("unchecked")
  public <S extends T> Join<S> as(Class<S> subType) {
    if (!EntityHelper.isEntity(subType)) {
      throw new IllegalStateException("can not cast a non-entity class");
    }
    Join<S> join = (Join<S>) this;
    join.pathResolver.resetPathSpecifier(subType);
    join.joinClause.setTreatAsType(subType);
    return join;
  }

  public class On {
    public T getPathSpecifier() {
      return pathResolver.getPathSpecifier();
    }
  }
}
