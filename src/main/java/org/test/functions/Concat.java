package org.test.functions;

import org.test.querystring.JpqlStringWriter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Concat extends JpqlFunction<String> {
  private final List<Object> parameters;

  public Concat(String... parameters) {
    this.parameters = toList(parameters);
  }

  @SafeVarargs
  public Concat(JpqlFunction<String>... nested) {
    parameters = toList(nested);
  }

  private static List<Object> toList(Object[] array) {
    return Arrays.stream(array).collect(Collectors.toList());
  }

  public Concat concat(JpqlFunction<String> nested) {
    parameters.add(nested);
    return this;
  }

  public Concat concat(String parameter) {
    parameters.add(parameter);
    return this;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("concat(");
    appendParameters(stringWriter);
    stringWriter.appendString(")");
  }

  private void appendParameters(JpqlStringWriter stringWriter) {
    for (int i = 0; i < parameters.size(); i++) {
      if (i > 0) {
        stringWriter.appendString(", ");
      }
      writeOperand(parameters.get(i), stringWriter);
    }
  }
}
