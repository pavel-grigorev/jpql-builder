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

  public static Ltrim ltrim(String parameter) {
    return new Ltrim(parameter);
  }

  public static Ltrim ltrim(String parameter, char trimChar) {
    return new Ltrim(parameter, trimChar);
  }

  public static Ltrim ltrim(JpqlFunction<String> nested) {
    return new Ltrim(nested);
  }

  public static Ltrim ltrim(JpqlFunction<String> nested, char trimChar) {
    return new Ltrim(nested, trimChar);
  }

  public static Rtrim rtrim(String parameter) {
    return new Rtrim(parameter);
  }

  public static Rtrim rtrim(String parameter, char trimChar) {
    return new Rtrim(parameter, trimChar);
  }

  public static Rtrim rtrim(JpqlFunction<String> nested) {
    return new Rtrim(nested);
  }

  public static Rtrim rtrim(JpqlFunction<String> nested, char trimChar) {
    return new Rtrim(nested, trimChar);
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
}
