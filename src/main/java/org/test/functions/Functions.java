package org.test.functions;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

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

  public static <T extends Number> Sub<T> sub(Number argument1, Number argument2) {
    return new Sub<>(argument1, argument2);
  }

  public static <T extends Number> Sub<T> sub(JpqlFunction<? extends Number> argument1, Number argument2) {
    return new Sub<>(argument1, argument2);
  }

  public static <T extends Number> Sub<T> sub(Number argument1, JpqlFunction<? extends Number> argument2) {
    return new Sub<>(argument1, argument2);
  }

  public static <T extends Number> Sub<T> sub(JpqlFunction<? extends Number> argument1, JpqlFunction<? extends Number> argument2) {
    return new Sub<>(argument1, argument2);
  }

  public static <T extends Number> Multi<T> multi(Number argument1, Number argument2) {
    return new Multi<>(argument1, argument2);
  }

  public static <T extends Number> Multi<T> multi(JpqlFunction<? extends Number> argument1, Number argument2) {
    return new Multi<>(argument1, argument2);
  }

  public static <T extends Number> Multi<T> multi(Number argument1, JpqlFunction<? extends Number> argument2) {
    return new Multi<>(argument1, argument2);
  }

  public static <T extends Number> Multi<T> multi(JpqlFunction<? extends Number> argument1, JpqlFunction<? extends Number> argument2) {
    return new Multi<>(argument1, argument2);
  }

  public static <T extends Number> Div<T> div(Number argument1, Number argument2) {
    return new Div<>(argument1, argument2);
  }

  public static <T extends Number> Div<T> div(JpqlFunction<? extends Number> argument1, Number argument2) {
    return new Div<>(argument1, argument2);
  }

  public static <T extends Number> Div<T> div(Number argument1, JpqlFunction<? extends Number> argument2) {
    return new Div<>(argument1, argument2);
  }

  public static <T extends Number> Div<T> div(JpqlFunction<? extends Number> argument1, JpqlFunction<? extends Number> argument2) {
    return new Div<>(argument1, argument2);
  }

  public static <T extends Number> Abs<T> abs(Number argument) {
    return new Abs<>(argument);
  }

  public static <T extends Number> Abs<T> abs(JpqlFunction<? extends Number> argument) {
    return new Abs<>(argument);
  }

  public static <T extends Number> Mod<T> mod(Div<T> argument) {
    return new Mod<>(argument);
  }

  public static <T extends Number> Sqrt<T> sqrt(Number argument) {
    return new Sqrt<>(argument);
  }

  public static <T extends Number> Sqrt<T> sqrt(JpqlFunction<? extends Number> argument) {
    return new Sqrt<>(argument);
  }

  public static CurrentDate currentDate() {
    return new CurrentDate();
  }

  public static CurrentTime currentTime() {
    return new CurrentTime();
  }

  public static CurrentTimestamp currentTimestamp() {
    return new CurrentTimestamp();
  }

  public static <P> Case<P> _case(P expression) {
    return new Case<>(expression);
  }

  public static <P> Case<P> _case(JpqlFunction<P> expression) {
    return new Case<>(expression);
  }

  public static CasePredicate _case() {
    return new CasePredicate();
  }

  @SafeVarargs
  public static <T> Coalesce<T> coalesce(T... parameters) {
    return new Coalesce<>(parameters);
  }

  @SafeVarargs
  public static <T> Coalesce<T> coalesce(JpqlFunction<T>... parameters) {
    return new Coalesce<>(parameters);
  }

  public static <T> Nullif<T> nullif(T argument1, T argument2) {
    return new Nullif<>(argument1, argument2);
  }

  public static <T> Nullif<T> nullif(JpqlFunction<T> argument1, T argument2) {
    return new Nullif<>(argument1, argument2);
  }

  public static <T> Nullif<T> nullif(T argument1, JpqlFunction<T> argument2) {
    return new Nullif<>(argument1, argument2);
  }

  public static <T> Nullif<T> nullif(JpqlFunction<T> argument1, JpqlFunction<T> argument2) {
    return new Nullif<>(argument1, argument2);
  }

  public static <T> Cast<T> cast(Object argument, Cast.Type type) {
    return new Cast<>(argument, type);
  }

  public static Extract extract(Date argument, Extract.Part part) {
    return new Extract(argument, part);
  }

  public static Extract extract(JpqlFunction<Date> argument, Extract.Part part) {
    return new Extract(argument, part);
  }

  public static RegExp regexp(String argument, String regExp) {
    return new RegExp(argument, regExp);
  }

  public static RegExp regexp(JpqlFunction<String> argument, String regExp) {
    return new RegExp(argument, regExp);
  }

  public static Index index(Object argument) {
    return new Index(argument);
  }

  public static <T> Key<T> key(Map<T, ?> argument) {
    return new Key<>(argument);
  }

  public static <T> T value(Map<?, T> argument) {
    Collection<T> values = argument.values();
    if (values.isEmpty()) {
      throw new IllegalArgumentException("argument is an empty map");
    }
    return values.iterator().next();
  }

  public static Size size(Collection<?> argument) {
    return new Size(argument);
  }

  public static Type type(Object argument) {
    return new Type(argument);
  }

  public static <T> Function<T> function(String name, Collection<?> arguments) {
    return new Function<>(name, arguments);
  }

  public static <T> Function<T> function(String name, Object... arguments) {
    return new Function<>(name, Arrays.asList(arguments));
  }

  public static <T> Func<T> func(String name, Collection<?> arguments) {
    return new Func<>(name, arguments);
  }

  public static <T> Func<T> func(String name, Object... arguments) {
    return new Func<>(name, Arrays.asList(arguments));
  }

  public static <T> Sql<T> sql(String sql, Collection<?> arguments) {
    return new Sql<>(sql, arguments);
  }

  public static <T> Sql<T> sql(String sql, Object... arguments) {
    return new Sql<>(sql, Arrays.asList(arguments));
  }

  public static <T> Column<T> column(String name, Object entity) {
    return new Column<>(name, entity);
  }
}
