package org.test.querystring;

import org.test.utils.AliasGenerator;
import org.test.path.PathResolver;
import org.test.path.PathResolverList;
import org.test.query.SelectQuery;
import org.test.utils.EntityHelper;

import java.util.HashMap;
import java.util.Map;

public class JpqlStringBuilder implements JpqlStringWriter {
  private final PathResolver<?> pathResolver;
  private final PathResolverList joins;
  private final StringBuilder builder = new StringBuilder();
  private final Map<String, Object> parameters = new HashMap<>();
  private final AliasGenerator aliasGenerator = new AliasGenerator();

  public JpqlStringBuilder(PathResolver<?> pathResolver, PathResolverList joins) {
    this.pathResolver = pathResolver;
    this.joins = joins;
  }

  public String build(SelectQuery query) {
    builder.delete(0, builder.length());
    parameters.clear();
    aliasGenerator.reset();

    query.writeTo(this);
    return builder.toString();
  }

  public Map<String, Object> getParameters() {
    return parameters;
  }

  @Override
  public void appendString(String string) {
    builder.append(string);
  }

  @Override
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
