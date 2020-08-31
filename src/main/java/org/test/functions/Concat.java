package org.test.functions;

import org.test.querystring.JpqlStringWriter;

import java.util.Arrays;
import java.util.List;

public class Concat extends JpqlFunction<String> {
  private final List<String> parameters;

  public Concat(String... parameters) {
    this(Arrays.asList(parameters));
  }

  public Concat(List<String> parameters) {
    if (parameters == null) {
      throw new NullPointerException("parameters must not be null");
    }
    this.parameters = parameters;
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
