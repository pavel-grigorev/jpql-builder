package org.thepavel.jpqlbuilder;

import org.thepavel.jpqlbuilder.operators.builders.ExpressionChain;
import org.thepavel.jpqlbuilder.path.PathResolver;
import org.thepavel.jpqlbuilder.query.JoinClause;
import org.thepavel.jpqlbuilder.utils.EntityHelper;

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
