package org.test.path;

import org.test.JpqlBuilderContext;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class PathResolver<T> {
  private final Map<String, Object> nameToValue = new HashMap<>();
  private final Map<Object, String> valueToName = new IdentityHashMap<>();
  private final PathResolverList children = new PathResolverList();
  private final String basePath;
  private final T pathSpecifier;
  private final JpqlBuilderContext context;

  public PathResolver(Class<T> entityClass, String basePath, JpqlBuilderContext context) {
    this.basePath = basePath;
    this.pathSpecifier = context.createProxy(entityClass, new GetterMethodInterceptor(this));
    this.context = context;
  }

  private PathResolver(T target, String basePath, JpqlBuilderContext context) {
    this.basePath = basePath;
    this.pathSpecifier = context.createProxy(target, new GetterMethodInterceptor(this));
    this.context = context;
  }

  JpqlBuilderContext getContext() {
    return context;
  }

  Object getValue(String propertyName) {
    return nameToValue.get(propertyName);
  }

  void put(String propertyName, Object value) {
    nameToValue.put(propertyName, value);
    valueToName.put(value, propertyName);
  }

  <E> PathResolver<E> createChildResolver(E target, String propertyName) {
    PathResolver<E> pathResolver = new PathResolver<>(target, buildPath(propertyName), context);
    children.add(pathResolver);
    return pathResolver;
  }

  private String buildPath(String propertyName) {
    return basePath + '.' + propertyName;
  }

  public T getPathSpecifier() {
    return pathSpecifier;
  }

  public String getPropertyPath(Object value) {
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
