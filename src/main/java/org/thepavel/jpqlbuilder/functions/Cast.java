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

public class Cast<T> extends JpqlFunction<T> {
  private final Object argument;
  private final Type type;

  public Cast(Object argument, Type type) {
    this.argument = argument;
    this.type = type;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("cast(");
    writeOperand(argument, stringWriter);
    stringWriter.appendString(" ");
    stringWriter.appendString(type.name);
    stringWriter.appendString(")");
  }

  public enum Type {
    BYTE("byte"),
    SHORT("short"),
    INTEGER("integer"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    CHAR("character"),
    BOOLEAN("boolean"),
    YES_NO("yes_no"),
    TRUE_FALSE("true_false"),
    STRING("string"),
    DATE("date"),
    TIME("time"),
    TIMESTAMP("timestamp"),
    CALENDAR("calendar"),
    CALENDAR_DATE("calendar_date"),
    BIG_DECIMAL("big_decimal"),
    BIG_INTEGER("big_integer"),
    LOCALE("locale"),
    TIME_ZONE("timezone"),
    CURRENCY("currency"),
    CLASS("class"),
    BINARY("binary"),
    TEXT("text"),
    SERIALIZABLE("serializable"),
    CLOB("clob"),
    BLOB("blob"),
    IMM_DATE("imm_date"),
    IMM_TIME("imm_time"),
    IMM_TIMESTAMP("imm_timestamp"),
    IMM_CALENDAR("imm_calendar"),
    IMM_CALENDAR_DATE("imm_calendar_date"),
    IMM_SERIALIZABLE("imm_serializable"),
    IMM_BINARY("imm_binary");

    private final String name;

    Type(String name) {
      this.name = name;
    }
  }
}
