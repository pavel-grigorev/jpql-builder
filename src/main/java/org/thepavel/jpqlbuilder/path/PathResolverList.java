package org.thepavel.jpqlbuilder.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathResolverList {
  private final List<PathResolver<?>> list = new ArrayList<>();

  public void add(PathResolver<?> pathResolver) {
    list.add(pathResolver);
  }

  public String getPropertyPath(Object value) {
    return list
        .stream()
        .map(r -> r.getPropertyPath(value))
        .filter(Objects::nonNull)
        .findFirst()
        .orElse(null);
  }
}
