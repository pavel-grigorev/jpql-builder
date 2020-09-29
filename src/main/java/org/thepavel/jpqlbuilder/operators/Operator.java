package org.thepavel.jpqlbuilder.operators;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public interface Operator {
  void writeTo(JpqlStringWriter stringWriter);

  default void writeOperand(Object operand, JpqlStringWriter stringWriter) {
    if (operand instanceof Operator) {
      ((Operator) operand).writeTo(stringWriter);
    } else {
      stringWriter.appendValue(operand);
    }
  }
}
