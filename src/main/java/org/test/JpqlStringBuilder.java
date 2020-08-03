package org.test;

import org.test.utils.EntityHelper;

import java.util.HashMap;
import java.util.Map;

public class JpqlStringBuilder<T> {
  private final PathResolver<T> pathResolver;
  private final StringBuilder builder = new StringBuilder();
  private final Map<String, Object> parameters = new HashMap<>();

  // TODO: build alias factory to support more aliases
  private char currentParameterAlias = 'a';

  JpqlStringBuilder(PathResolver<T> pathResolver) {
    this.pathResolver = pathResolver;
  }

  void buildBaseQuery(Class<T> entityClass, String alias) {
    builder
        .append("select ")
        .append(alias)
        .append(" from ")
        .append(EntityHelper.getEntityName(entityClass))
        .append(' ')
        .append(alias);
  }

  String build() {
    return builder.toString();
  }

  Map<String, Object> getParameters() {
    return parameters;
  }

  public void appendString(String s) {
    builder.append(s);
  }

  public void appendValue(Object value) {
    String path = pathResolver.getPropertyPath(value);
    if (path != null) {
      builder.append(path);
    } else {
      appendParameter(value);
    }
  }

  private void appendParameter(Object value) {
    String alias = getNextParameterAlias();
    parameters.put(alias, value);
    builder.append(':').append(alias);
  }

  private String getNextParameterAlias() {
    return String.valueOf(currentParameterAlias++);
  }
}
