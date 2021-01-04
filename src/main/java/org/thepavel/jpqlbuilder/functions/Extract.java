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

import java.util.Date;

public class Extract extends JpqlFunction<Integer> {
  private final Object argument;
  private final Part part;

  public Extract(Date argument, Part part) {
    this.argument = argument;
    this.part = part;
  }

  public Extract(JpqlFunction<Date> argument, Part part) {
    this.argument = argument;
    this.part = part;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("extract(");
    stringWriter.appendString(part.name);
    stringWriter.appendString(" from ");
    writeOperand(argument, stringWriter);
    stringWriter.appendString(")");
  }

  public enum Part {
    YEAR("YEAR"),
    MONTH("MONTH"),
    DAY("DAY"),
    HOUR("HOUR"),
    MINUTE("MINUTE"),
    SECOND("SECOND");

    private final String name;

    Part(String name) {
      this.name = name;
    }
  }
}
