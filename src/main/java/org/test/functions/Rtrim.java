package org.test.functions;

public class Rtrim extends Ltrim {
  public Rtrim(String parameter) {
    super(parameter);
  }

  public Rtrim(String parameter, char trimChar) {
    super(parameter, trimChar);
  }

  public Rtrim(JpqlFunction<String> nested) {
    super(nested);
  }

  public Rtrim(JpqlFunction<String> nested, char trimChar) {
    super(nested, trimChar);
  }

  @Override
  String getTrimTypeKeyword() {
    return "trailing ";
  }
}
