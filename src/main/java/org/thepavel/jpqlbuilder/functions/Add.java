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

public class Add<T extends Number> extends JpqlFunction<T> {
  private final Object argument1;
  private final Object argument2;

  public Add(Number argument1, Number argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Add(JpqlFunction<? extends Number> argument1, Number argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Add(Number argument1, JpqlFunction<? extends Number> argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  public Add(JpqlFunction<? extends Number> argument1, JpqlFunction<? extends Number> argument2) {
    this.argument1 = argument1;
    this.argument2 = argument2;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(argument1, stringWriter);
    stringWriter.appendString(" + ");
    writeOperand(argument2, stringWriter);
  }
}
