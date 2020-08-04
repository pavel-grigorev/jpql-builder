package org.test.operators;

import org.apache.commons.lang3.StringUtils;
import org.test.JpqlStringBuilder;

public class Like extends TernaryOperator<String, String, String> {
  public Like(String operandA, String operandB) {
    this(operandA, operandB, null);
  }

  public Like(String operandA, String operandB, String escapeChar) {
    super(operandA, operandB, escapeChar);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);
    stringBuilder.appendString(getLikeOperator());
    writeOperand(operandB, stringBuilder);
    appendEscapeChar(stringBuilder);
  }

  String getLikeOperator() {
    return " like ";
  }

  private void appendEscapeChar(JpqlStringBuilder<?> stringBuilder) {
    if (StringUtils.isNotBlank(operandC)) {
      stringBuilder.appendString(" escape ");
      writeOperand(operandC, stringBuilder);
    }
  }
}
