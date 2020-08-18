package org.test.operators;

import org.test.querystring.JpqlStringWriter;

public class NotEqual<T> extends BinaryOperator<T, T> {
  public NotEqual(T operandA, T operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);

    if (operandB == null) {
      stringWriter.appendString(" is not null");
    } else {
      stringWriter.appendString(" <> ");
      writeOperand(operandB, stringWriter);
    }
  }
}
