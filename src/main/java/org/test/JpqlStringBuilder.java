package org.test;

import org.test.query.SelectQuery;
import org.test.utils.EntityHelper;

import java.util.HashMap;
import java.util.Map;

public class JpqlStringBuilder<T> {
  private final PathResolver<T> pathResolver;
  private final PathResolverList joins;
  private final StringBuilder builder = new StringBuilder();
  private final Map<String, Object> parameters = new HashMap<>();
  private final AliasGenerator aliasGenerator = new AliasGenerator();

  JpqlStringBuilder(PathResolver<T> pathResolver, PathResolverList joins) {
    this.pathResolver = pathResolver;
    this.joins = joins;
  }

  String build(SelectQuery query) {
    builder.delete(0, builder.length());
    parameters.clear();
    aliasGenerator.reset();

    query.writeTo(this);
    return builder.toString();
  }

  Map<String, Object> getParameters() {
    return parameters;
  }

  public void appendString(String s) {
    builder.append(s);
  }

  public void appendValue(Object value) {
    if (isEntityClass(value)) {
      builder.append(getEntityName(value));
      return;
    }
    String path = getPropertyPath(value);
    if (path != null) {
      builder.append(path);
      return;
    }
    appendParameter(value);
  }

  private static boolean isEntityClass(Object value) {
    return value instanceof Class && EntityHelper.isEntity((Class<?>) value);
  }

  private static String getEntityName(Object value) {
    return EntityHelper.getEntityName((Class<?>) value);
  }

  private String getPropertyPath(Object value) {
    String path = pathResolver.getPropertyPath(value);
    return path != null ? path : joins.getPropertyPath(value);
  }

  private void appendParameter(Object value) {
    String alias = aliasGenerator.next();
    parameters.put(alias, value);
    builder.append(':').append(alias);
  }
}
