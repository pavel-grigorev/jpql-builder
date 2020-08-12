package org.test;

public class JpqlBuilderJoinOnChain<T> {
  private final PathResolver<T> pathResolver;

  JpqlBuilderJoinOnChain(PathResolver<T> pathResolver) {
    this.pathResolver = pathResolver;
  }

  public T getPathSpecifier() {
    return pathResolver.getPathSpecifier();
  }
}
