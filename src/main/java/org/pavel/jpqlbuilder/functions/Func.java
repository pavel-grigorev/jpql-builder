package org.pavel.jpqlbuilder.functions;

import java.util.Collection;

public class Func<T> extends Function<T> {
  public Func(String name, Collection<?> arguments) {
    super(name, arguments);
  }

  @Override
  String getFunctionName() {
    return "func";
  }
}
