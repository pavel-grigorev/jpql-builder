package org.test.functions;

public class Div<T extends Number> extends Add<T> {
  public Div(Number argument1, Number argument2) {
    super(argument1, argument2);
  }

  public Div(JpqlFunction<? extends Number> argument1, Number argument2) {
    super(argument1, argument2);
  }

  public Div(Number argument1, JpqlFunction<? extends Number> argument2) {
    super(argument1, argument2);
  }

  public Div(JpqlFunction<? extends Number> argument1, JpqlFunction<? extends Number> argument2) {
    super(argument1, argument2);
  }

  @Override
  String getOperator() {
    return " / ";
  }
}
