package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.Collection;

public class Function<T> extends JpqlFunction<T> {
  private final String name;
  private final Collection<?> arguments;

  public Function(String name, Collection<?> arguments) {
    if (name == null || arguments == null) {
      throw new NullPointerException();
    }
    if (name.indexOf('\'') != -1) {
      throw new IllegalArgumentException("single quote character is not allowed");
    }
    this.name = name;
    this.arguments = arguments;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString(getFunctionName());
    stringWriter.appendString("('");
    stringWriter.appendString(name);
    stringWriter.appendString("'");
    appendArguments(stringWriter);
    stringWriter.appendString(")");
  }

  String getFunctionName() {
    return "function";
  }

  private void appendArguments(JpqlStringWriter stringWriter) {
    for (Object argument : arguments) {
      stringWriter.appendString(", ");
      writeOperand(argument, stringWriter);
    }
  }
}
