/*
 * Copyright (c) 2020 Pavel Grigorev.
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

public class Trim extends JpqlFunction<String> {
  private static final char DEFAULT_TRIM_CHAR = ' ';

  private final Object parameter;
  private final char trimChar;

  Trim(String parameter) {
    this(parameter, DEFAULT_TRIM_CHAR);
  }

  Trim(String parameter, char trimChar) {
    this.parameter = parameter;
    this.trimChar = trimChar;
  }

  Trim(JpqlFunction<String> nested) {
    this(nested, DEFAULT_TRIM_CHAR);
  }

  Trim(JpqlFunction<String> nested, char trimChar) {
    this.parameter = nested;
    this.trimChar = trimChar;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("trim(");
    appendTrimChar(stringWriter);
    writeOperand(parameter, stringWriter);
    stringWriter.appendString(")");
  }

  private void appendTrimChar(JpqlStringWriter stringWriter) {
    if (trimChar != DEFAULT_TRIM_CHAR) {
      writeOperand(trimChar, stringWriter);
      stringWriter.appendString(" from ");
    }
  }
}
