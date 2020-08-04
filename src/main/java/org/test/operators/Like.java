package org.test.operators;

import org.apache.commons.lang3.StringUtils;
import org.test.JpqlStringBuilder;

public class Like extends BinaryOperator<String, String> {
  private final String escapeChar;

  public Like(String operandA, String operandB) {
    this(operandA, operandB, null);
  }

  public Like(String operandA, String operandB, String escapeChar) {
    super(operandA, operandB);
    this.escapeChar = escapeChar;
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    writeOperand(operandA, stringBuilder);
    stringBuilder.appendString(getLikeOperator());
    writeOperand(operandB, stringBuilder);

    if (StringUtils.isNotBlank(escapeChar)) {
      stringBuilder.appendString(" escape ");
      writeOperand(escapeChar, stringBuilder);
    }
  }

  String getLikeOperator() {
    return " like ";
  }
}
