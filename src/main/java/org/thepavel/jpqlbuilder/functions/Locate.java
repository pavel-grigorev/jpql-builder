/*
 * Copyright (c) 2020-2021 Pavel Grigorev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thepavel.jpqlbuilder.functions;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

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
