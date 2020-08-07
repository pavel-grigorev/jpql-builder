package org.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class PathResolverList {
  private final List<PathResolver<?>> list = new ArrayList<>();

  void add(PathResolver<?> pathResolver) {
    list.add(pathResolver);
  }

  String getPropertyPath(Object value) {
    return list
        .stream()
        .map(r -> r.getPropertyPath(value))
        .filter(Objects::nonNull)
        .findFirst()
        .orElse(null);
  }
}
