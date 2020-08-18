package org.test.operators;

import org.test.querystring.JpqlStringWriter;

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
