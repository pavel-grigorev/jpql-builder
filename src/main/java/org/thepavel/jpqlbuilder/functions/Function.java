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
