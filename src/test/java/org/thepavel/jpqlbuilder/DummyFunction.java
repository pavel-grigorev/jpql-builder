package org.thepavel.jpqlbuilder;

import org.thepavel.jpqlbuilder.functions.JpqlFunction;
import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class DummyFunction<T> extends JpqlFunction<T> {
  private final Object operand;

  public DummyFunction(T operand) {
    this.operand = operand;
  }

  public static <T> DummyFunction<T> dummy(T operand) {
    return new DummyFunction<>(operand);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("dummy(");
    writeOperand(operand, stringWriter);
    stringWriter.appendString(")");
  }
}
