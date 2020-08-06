package org.test.functions;

public class Functions {
  private Functions() {
  }

  public static Lower lower(String operand) {
    return new Lower(operand);
  }

  public static Upper upper(String operand) {
    return new Upper(operand);
  }
}
