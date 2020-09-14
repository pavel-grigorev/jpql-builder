package org.test.path;

import org.test.JpqlBuilderContext;
import org.test.utils.EntityHelper;
import org.test.utils.ReflectionHelper;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class PathResolver<T> {
  private final Map<String, Object> nameToValue = new HashMap<>();
  private final Map<Object, String> valueToName = new IdentityHashMap<>();
  private final PathResolverList children = new PathResolverList();
  private final String basePath;
  private T pathSpecifier;
  private final JpqlBuilderContext context;

  public PathResolver(Class<T> entityClass, String basePath, JpqlBuilderContext context) {
    this.basePath = basePath;
    this.context = context;
    resetPathSpecifier(entityClass);
  }

  @SuppressWarnings("unchecked")
  public PathResolver(Map<Object, Object> target, String basePath, JpqlBuilderContext context) {
    if (target == null || target.isEmpty()) {
      throw new IllegalArgumentException("can not create a path resolver for a null or empty map");
    }

    this.basePath = basePath;
    this.context = context;

    Map.Entry<Object, Object> entry = target.entrySet().iterator().next();
    Object key = entry.getKey();
    Object value = entry.getValue();
    Class<?> valueType = value.getClass();

    if (EntityHelper.isEntity(valueType)) {
      value = createChildResolverForMapValue(valueType).getPathSpecifier();
    }

    Map<Object, Object> clone = ReflectionHelper.newInstance(target.getClass());
    clone.put(key, value);

    this.pathSpecifier = (T) clone;
  }

  public void resetPathSpecifier(Class<T> entityClass) {
    pathSpecifier = context.createProxy(entityClass, new GetterMethodInterceptor(this));
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

  <E> PathResolver<E> createChildResolver(Class<E> entityClass, String propertyName) {
    PathResolver<E> pathResolver = new PathResolver<>(entityClass, buildPath(propertyName), context);
    children.add(pathResolver);
    return pathResolver;
  }

  private String buildPath(String propertyName) {
    return basePath + '.' + propertyName;
  }

  <E> PathResolver<E> createChildResolverForMapValue(Class<E> entityClass) {
    PathResolver<E> pathResolver = new PathResolver<>(entityClass, buildMapValueAlias(), context);
    children.add(pathResolver);
    return pathResolver;
  }

  private String buildMapValueAlias() {
    return "value(" + basePath + ")";
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
