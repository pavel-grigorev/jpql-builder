package org.pavel.jpqlbuilder.functions;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.List;

import static org.pavel.jpqlbuilder.utils.CollectionUtils.toList;

public class Coalesce<T> extends JpqlFunction<T> {
  private final List<Object> parameters;

  @SafeVarargs
  public Coalesce(T... parameters) {
    this.parameters = toList(parameters);
  }

  @SafeVarargs
  public Coalesce(JpqlFunction<T>... parameters) {
    this.parameters = toList(parameters);
  }

  public Coalesce<T> coalesce(T parameter) {
    parameters.add(parameter);
    return this;
  }

  public Coalesce<T> coalesce(JpqlFunction<T> parameter) {
    parameters.add(parameter);
    return this;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("coalesce(");
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
