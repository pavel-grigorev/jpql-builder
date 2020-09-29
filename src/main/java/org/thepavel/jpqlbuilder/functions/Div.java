package org.thepavel.jpqlbuilder.functions;

public class Div<T extends Number> extends Multi<T> {
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
  boolean shouldWrap(Class<?> type) {
    return type == Add.class || type == Sub.class || type == Multi.class || type == Div.class;
  }

  @Override
  String getOperator() {
    return " / ";
  }
}
