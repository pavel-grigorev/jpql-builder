package org.test.functions;

public class Multi<T extends Number> extends Add<T> {
  public Multi(Number argument1, Number argument2) {
    super(argument1, argument2);
  }

  public Multi(JpqlFunction<? extends Number> argument1, Number argument2) {
    super(argument1, argument2);
  }

  public Multi(Number argument1, JpqlFunction<? extends Number> argument2) {
    super(argument1, argument2);
  }

  public Multi(JpqlFunction<? extends Number> argument1, JpqlFunction<? extends Number> argument2) {
    super(argument1, argument2);
  }

  @Override
  String getOperator() {
    return " * ";
  }
}
