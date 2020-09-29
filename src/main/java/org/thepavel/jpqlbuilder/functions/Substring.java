package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Substring extends JpqlFunction<String> {
  private final Object string;
  private final Object index;
  private final Object length;

  public Substring(String string, Integer index) {
    this.string = string;
    this.index = index;
    this.length = null;
  }

  public Substring(String string, JpqlFunction<Integer> index) {
    this.string = string;
    this.index = index;
    this.length = null;
  }

  public Substring(JpqlFunction<String> string, Integer index) {
    this.string = string;
    this.index = index;
    this.length = null;
  }

  public Substring(JpqlFunction<String> string, JpqlFunction<Integer> index) {
    this.string = string;
    this.index = index;
    this.length = null;
  }

  public Substring(String string, Integer index, Integer length) {
    this.string = string;
    this.index = index;
    this.length = length;
  }

  public Substring(String string, JpqlFunction<Integer> index, Integer length) {
    this.string = string;
    this.index = index;
    this.length = length;
  }

  public Substring(String string, Integer index, JpqlFunction<Integer> length) {
    this.string = string;
    this.index = index;
    this.length = length;
  }

  public Substring(String string, JpqlFunction<Integer> index, JpqlFunction<Integer> length) {
    this.string = string;
    this.index = index;
    this.length = length;
  }

  public Substring(JpqlFunction<String> string, Integer index, Integer length) {
    this.string = string;
    this.index = index;
    this.length = length;
  }

  public Substring(JpqlFunction<String> string, JpqlFunction<Integer> index, Integer length) {
    this.string = string;
    this.index = index;
    this.length = length;
  }

  public Substring(JpqlFunction<String> string, Integer index, JpqlFunction<Integer> length) {
    this.string = string;
    this.index = index;
    this.length = length;
  }

  public Substring(JpqlFunction<String> string, JpqlFunction<Integer> index, JpqlFunction<Integer> length) {
    this.string = string;
    this.index = index;
    this.length = length;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("substring(");
    writeOperand(string, stringWriter);
    stringWriter.appendString(", ");
    writeOperand(index, stringWriter);
    appendLength(stringWriter);
    stringWriter.appendString(")");
  }

  private void appendLength(JpqlStringWriter stringWriter) {
    if (length != null) {
      stringWriter.appendString(", ");
      writeOperand(length, stringWriter);
    }
  }
}
