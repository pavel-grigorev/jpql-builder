package org.test.functions;

public class Sub<T extends Number> extends Add<T> {
  public Sub(Number argument1, Number argument2) {
    super(argument1, argument2);
  }

  public Sub(JpqlFunction<? extends Number> argument1, Number argument2) {
    super(argument1, argument2);
  }

  public Sub(Number argument1, JpqlFunction<? extends Number> argument2) {
    super(argument1, argument2);
  }

  public Sub(JpqlFunction<? extends Number> argument1, JpqlFunction<? extends Number> argument2) {
    super(argument1, argument2);
  }

  @Override
  String getOperator() {
    return " - ";
  }
}
