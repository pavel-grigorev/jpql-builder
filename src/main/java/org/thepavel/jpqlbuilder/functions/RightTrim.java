package org.thepavel.jpqlbuilder.functions;

public class RightTrim extends LeftTrim {
  public RightTrim(String parameter) {
    super(parameter);
  }

  public RightTrim(String parameter, char trimChar) {
    super(parameter, trimChar);
  }

  public RightTrim(JpqlFunction<String> nested) {
    super(nested);
  }

  public RightTrim(JpqlFunction<String> nested, char trimChar) {
    super(nested, trimChar);
  }

  @Override
  String getTrimTypeKeyword() {
    return "trailing ";
  }
}
