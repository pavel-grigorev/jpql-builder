package org.pavel.jpqlbuilder.operators;

import org.apache.commons.lang3.StringUtils;
import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Like extends TernaryOperator<Object, Object, String> {
  public Like(Object operandA, Object operandB) {
    this(operandA, operandB, null);
  }

  public Like(Object operandA, Object operandB, String escapeChar) {
    super(operandA, operandB, escapeChar);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(operandA, stringWriter);
    stringWriter.appendString(getLikeOperator());
    writeOperand(operandB, stringWriter);
    appendEscapeChar(stringWriter);
  }

  String getLikeOperator() {
    return " like ";
  }

  private void appendEscapeChar(JpqlStringWriter stringWriter) {
    if (StringUtils.isNotBlank(operandC)) {
      stringWriter.appendString(" escape ");
      writeOperand(operandC, stringWriter);
    }
  }
}
