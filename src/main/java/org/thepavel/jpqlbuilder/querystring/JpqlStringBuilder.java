package org.thepavel.jpqlbuilder.querystring;

import org.thepavel.jpqlbuilder.utils.AliasGenerator;
import org.thepavel.jpqlbuilder.path.PathResolver;
import org.thepavel.jpqlbuilder.path.PathResolverList;
import org.thepavel.jpqlbuilder.query.SelectQuery;
import org.thepavel.jpqlbuilder.utils.EntityHelper;

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

  @Override
  public String toString() {
    return builder.toString();
  }

  public String build(SelectQuery query) {
    builder.delete(0, builder.length());
    parameters.clear();
    aliasGenerator.reset();

    query.writeTo(this);
    return toString();
  }

  public Map<String, Object> getParameters() {
    return new HashMap<>(parameters);
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
