package org.test.functions;

import java.util.Collection;

public class Sql<T> extends Function<T> {
  public Sql(String sql, Collection<?> arguments) {
    super(sql, arguments);
  }

  @Override
  String getFunctionName() {
    return "sql";
  }
}
