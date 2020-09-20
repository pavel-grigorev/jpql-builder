package org.pavel.jpqlbuilder.functions;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;

public class Locate extends JpqlFunction<Integer> {
  private final Object searchString;
  private final Object string;
  private final Object position;

  public Locate(String searchString, String string) {
    this.searchString = searchString;
    this.string = string;
    this.position = null;
  }

  public Locate(String searchString, JpqlFunction<String> string) {
    this.searchString = searchString;
    this.string = string;
    this.position = null;
  }

  public Locate(JpqlFunction<String> searchString, String string) {
    this.searchString = searchString;
    this.string = string;
    this.position = null;
  }

  public Locate(JpqlFunction<String> searchString, JpqlFunction<String> string) {
    this.searchString = searchString;
    this.string = string;
    this.position = null;
  }

  public Locate(String searchString, String string, Integer position) {
    this.searchString = searchString;
    this.string = string;
    this.position = position;
  }

  public Locate(String searchString, JpqlFunction<String> string, Integer position) {
    this.searchString = searchString;
    this.string = string;
    this.position = position;
  }

  public Locate(String searchString, String string, JpqlFunction<Integer> position) {
    this.searchString = searchString;
    this.string = string;
    this.position = position;
  }

  public Locate(String searchString, JpqlFunction<String> string, JpqlFunction<Integer> position) {
    this.searchString = searchString;
    this.string = string;
    this.position = position;
  }

  public Locate(JpqlFunction<String> searchString, String string, Integer position) {
    this.searchString = searchString;
    this.string = string;
    this.position = position;
  }

  public Locate(JpqlFunction<String> searchString, JpqlFunction<String> string, Integer position) {
    this.searchString = searchString;
    this.string = string;
    this.position = position;
  }

  public Locate(JpqlFunction<String> searchString, String string, JpqlFunction<Integer> position) {
    this.searchString = searchString;
    this.string = string;
    this.position = position;
  }

  public Locate(JpqlFunction<String> searchString, JpqlFunction<String> string, JpqlFunction<Integer> position) {
    this.searchString = searchString;
    this.string = string;
    this.position = position;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("locate(");
    writeOperand(searchString, stringWriter);
    stringWriter.appendString(", ");
    writeOperand(string, stringWriter);
    appendPosition(stringWriter);
    stringWriter.appendString(")");
  }

  private void appendPosition(JpqlStringWriter stringWriter) {
    if (position != null) {
      stringWriter.appendString(", ");
      writeOperand(position, stringWriter);
    }
  }
}
