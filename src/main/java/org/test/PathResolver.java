package org.test;

import org.test.utils.ProxyFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class PathResolver<T> {
  private final Map<String, Object> nameToValue = new HashMap<>();
  private final Map<Object, String> valueToName = new IdentityHashMap<>();
  private final List<PathResolver<?>> children = new ArrayList<>();
  private final String basePath;
  private final T pathSpecifier;

  PathResolver(Class<T> entityClass, String basePath) {
    this.basePath = basePath;
    this.pathSpecifier = ProxyFactory.createProxy(entityClass, new GetterMethodInterceptor(this));
  }

  Object getValue(String propertyName) {
    return nameToValue.get(propertyName);
  }

  void put(String propertyName, Object value) {
    nameToValue.put(propertyName, value);
    valueToName.put(value, propertyName);
  }

  <E> PathResolver<E> createChildResolver(Class<E> entityClass, String propertyName) {
    PathResolver<E> pathResolver = new PathResolver<>(entityClass, buildPath(propertyName));
    children.add(pathResolver);
    return pathResolver;
  }

  private String buildPath(String propertyName) {
    return basePath + '.' + propertyName;
  }

  T getPathSpecifier() {
    return pathSpecifier;
  }

  String getPropertyPath(Object value) {
    if (value == pathSpecifier) {
      return basePath;
    }
    String propertyPath = getPropertyPathInternal(value);
    return propertyPath != null ? propertyPath : findPathInChildren(value);
  }

  private String getPropertyPathInternal(Object value) {
    String propertyName = valueToName.get(value);
    return propertyName != null ? buildPath(propertyName) : null;
  }

  private String findPathInChildren(Object value) {
    return children
        .stream()
        .map(c -> c.getPropertyPath(value))
        .filter(Objects::nonNull)
        .findFirst()
        .orElse(null);
  }
}
