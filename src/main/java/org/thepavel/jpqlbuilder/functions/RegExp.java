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

public class RegExp extends JpqlFunction<Boolean> {
  private final Object argument;
  private final String regExp;

  public RegExp(String argument, String regExp) {
    this.argument = argument;
    this.regExp = regExp;
  }

  public RegExp(JpqlFunction<String> argument, String regExp) {
    this.argument = argument;
    this.regExp = regExp;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    writeOperand(argument, stringWriter);
    stringWriter.appendString(" regexp ");
    writeOperand(regExp, stringWriter);
  }
}
