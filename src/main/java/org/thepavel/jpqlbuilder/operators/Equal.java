package org.thepavel.jpqlbuilder.operators;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Equal<T> extends BinaryOperator<T, T> {
  public Equal(T operandA, T operandB) {
    super(operandA, operandB);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);

    if (operandB == null) {
      stringWriter.appendString(" is null");
    } else {
      stringWriter.appendString(" = ");
      writeOperand(operandB, stringWriter);
    }
  }
}
