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

public class New extends JpqlFunction<Object> {
  private final Class<?> type;
  private final Object[] arguments;

  public New(Class<?> type, Object... arguments) {
    checkType(type);
    this.type = type;
    this.arguments = arguments;
  }

  private static void checkType(Class<?> type) {
    if (type.isPrimitive()) {
      throw new IllegalArgumentException("Primitive types are not supported");
    }
    if (type.isArray()) {
      throw new IllegalArgumentException("Arrays are not supported");
    }
    if (type.isEnum()) {
      throw new IllegalArgumentException("Enums are not supported");
    }
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("new ");
    stringWriter.appendString(type.getName());
    stringWriter.appendString("(");
    appendArguments(stringWriter);
    stringWriter.appendString(")");
  }

  private void appendArguments(JpqlStringWriter stringWriter) {
    for (int i = 0; i < arguments.length; i++) {
      if (i > 0) {
        stringWriter.appendString(", ");
      }
      writeOperand(arguments[i], stringWriter);
    }
  }
}
