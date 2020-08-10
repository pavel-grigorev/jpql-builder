package org.test;

import org.test.utils.ProxyFactory;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

class PathResolver<T> {
  private final Map<String, Object> nameToValue = new HashMap<>();
  private final Map<Object, String> valueToName = new IdentityHashMap<>();
  private final PathResolverList children = new PathResolverList();
  private final String basePath;
  private final T pathSpecifier;

  PathResolver(Class<T> entityClass, String basePath) {
    this.basePath = basePath;
    this.pathSpecifier = ProxyFactory.createProxy(entityClass, new GetterMethodInterceptor(this));
  }

  private PathResolver(T target, String basePath) {
    this.basePath = basePath;
    this.pathSpecifier = ProxyFactory.createProxy(target, new GetterMethodInterceptor(this));
  }

  Object getValue(String propertyName) {
    return nameToValue.get(propertyName);
  }

  void put(String propertyName, Object value) {
    nameToValue.put(propertyName, value);
    valueToName.put(value, propertyName);
  }

  <E> PathResolver<E> createChildResolver(E target, String propertyName) {
    PathResolver<E> pathResolver = new PathResolver<>(target, buildPath(propertyName));
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
    return propertyPath != null ? propertyPath : children.getPropertyPath(value);
  }

  private String getPropertyPathInternal(Object value) {
    String propertyName = valueToName.get(value);
    return propertyName != null ? buildPath(propertyName) : null;
  }
}
