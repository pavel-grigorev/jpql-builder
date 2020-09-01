package org.test.functions;

public class Functions {
  private Functions() {
  }

  public static Lower lower(String parameter) {
    return new Lower(parameter);
  }

  public static Lower lower(JpqlFunction<String> nested) {
    return new Lower(nested);
  }

  public static Upper upper(String parameter) {
    return new Upper(parameter);
  }

  public static Upper upper(JpqlFunction<String> nested) {
    return new Upper(nested);
  }

  public static Trim trim(String parameter) {
    return new Trim(parameter);
  }

  public static Trim trim(String parameter, char trimChar) {
    return new Trim(parameter, trimChar);
  }

  public static Trim trim(JpqlFunction<String> nested) {
    return new Trim(nested);
  }

  public static Trim trim(JpqlFunction<String> nested, char trimChar) {
    return new Trim(nested, trimChar);
  }

  public static LeftTrim leftTrim(String parameter) {
    return new LeftTrim(parameter);
  }

  public static LeftTrim leftTrim(String parameter, char trimChar) {
    return new LeftTrim(parameter, trimChar);
  }

  public static LeftTrim leftTrim(JpqlFunction<String> nested) {
    return new LeftTrim(nested);
  }

  public static LeftTrim leftTrim(JpqlFunction<String> nested, char trimChar) {
    return new LeftTrim(nested, trimChar);
  }

  public static RightTrim rightTrim(String parameter) {
    return new RightTrim(parameter);
  }

  public static RightTrim rightTrim(String parameter, char trimChar) {
    return new RightTrim(parameter, trimChar);
  }

  public static RightTrim rightTrim(JpqlFunction<String> nested) {
    return new RightTrim(nested);
  }

  public static RightTrim rightTrim(JpqlFunction<String> nested, char trimChar) {
    return new RightTrim(nested, trimChar);
  }

  public static Concat concat(String... parameters) {
    return new Concat(parameters);
  }

  @SafeVarargs
  public static Concat concat(JpqlFunction<String>... nested) {
    return new Concat(nested);
  }

  public static Substring substring(String string, Integer index) {
    return new Substring(string, index);
  }

  public static Substring substring(String string, JpqlFunction<Integer> index) {
    return new Substring(string, index);
  }

  public static Substring substring(JpqlFunction<String> string, Integer index) {
    return new Substring(string, index);
  }

  public static Substring substring(JpqlFunction<String> string, JpqlFunction<Integer> index) {
    return new Substring(string, index);
  }

  public static Substring substring(String string, Integer index, Integer length) {
    return new Substring(string, index, length);
  }

  public static Substring substring(String string, JpqlFunction<Integer> index, Integer length) {
    return new Substring(string, index, length);
  }

  public static Substring substring(String string, Integer index, JpqlFunction<Integer> length) {
    return new Substring(string, index, length);
  }

  public static Substring substring(String string, JpqlFunction<Integer> index, JpqlFunction<Integer> length) {
    return new Substring(string, index, length);
  }

  public static Substring substring(JpqlFunction<String> string, Integer index, Integer length) {
    return new Substring(string, index, length);
  }

  public static Substring substring(JpqlFunction<String> string, JpqlFunction<Integer> index, Integer length) {
    return new Substring(string, index, length);
  }

  public static Substring substring(JpqlFunction<String> string, Integer index, JpqlFunction<Integer> length) {
    return new Substring(string, index, length);
  }

  public static Substring substring(JpqlFunction<String> string, JpqlFunction<Integer> index, JpqlFunction<Integer> length) {
    return new Substring(string, index, length);
  }

  public static Length length(String string) {
    return new Length(string);
  }

  public static Length length(JpqlFunction<String> string) {
    return new Length(string);
  }

  public static Locate locate(String searchString, String string) {
    return new Locate(searchString, string);
  }

  public static Locate locate(String searchString, JpqlFunction<String> string) {
    return new Locate(searchString, string);
  }

  public static Locate locate(JpqlFunction<String> searchString, String string) {
    return new Locate(searchString, string);
  }

  public static Locate locate(JpqlFunction<String> searchString, JpqlFunction<String> string) {
    return new Locate(searchString, string);
  }

  public static Locate locate(String searchString, String string, Integer position) {
    return new Locate(searchString, string, position);
  }

  public static Locate locate(String searchString, JpqlFunction<String> string, Integer position) {
    return new Locate(searchString, string, position);
  }

  public static Locate locate(String searchString, String string, JpqlFunction<Integer> position) {
    return new Locate(searchString, string, position);
  }

  public static Locate locate(String searchString, JpqlFunction<String> string, JpqlFunction<Integer> position) {
    return new Locate(searchString, string, position);
  }

  public static Locate locate(JpqlFunction<String> searchString, String string, Integer position) {
    return new Locate(searchString, string, position);
  }

  public static Locate locate(JpqlFunction<String> searchString, JpqlFunction<String> string, Integer position) {
    return new Locate(searchString, string, position);
  }

  public static Locate locate(JpqlFunction<String> searchString, String string, JpqlFunction<Integer> position) {
    return new Locate(searchString, string, position);
  }

  public static Locate locate(JpqlFunction<String> searchString, JpqlFunction<String> string, JpqlFunction<Integer> position) {
    return new Locate(searchString, string, position);
  }

  public static <T extends Number> Add<T> add(Number argument1, Number argument2) {
    return new Add<>(argument1, argument2);
  }

  public static <T extends Number> Add<T> add(JpqlFunction<? extends Number> argument1, Number argument2) {
    return new Add<>(argument1, argument2);
  }

  public static <T extends Number> Add<T> add(Number argument1, JpqlFunction<? extends Number> argument2) {
    return new Add<>(argument1, argument2);
  }

  public static <T extends Number> Add<T> add(JpqlFunction<? extends Number> argument1, JpqlFunction<? extends Number> argument2) {
    return new Add<>(argument1, argument2);
  }
}
