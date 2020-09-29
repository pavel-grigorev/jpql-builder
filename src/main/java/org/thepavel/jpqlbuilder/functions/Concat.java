package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.utils.CollectionUtils;
import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.List;

public class Concat extends JpqlFunction<String> {
  private final List<Object> parameters;

  public Concat(String... parameters) {
    this.parameters = CollectionUtils.toList(parameters);
  }

  @SafeVarargs
  public Concat(JpqlFunction<String>... parameters) {
    this.parameters = CollectionUtils.toList(parameters);
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
