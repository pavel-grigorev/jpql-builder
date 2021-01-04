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

package org.thepavel.jpqlbuilder.query;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.thepavel.jpqlbuilder.operators.Operator;

import java.util.ArrayList;
import java.util.List;

public class SelectClause implements Operator {
  private final List<Object> selected = new ArrayList<>();
  private final List<Class<?>> from = new ArrayList<>();
  private final List<String> aliases = new ArrayList<>();

  public void addSelected(Object selected) {
    this.selected.add(selected);
  }

  public void addFrom(Class<?> type, String alias) {
    from.add(type);
    aliases.add(alias);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("select ");
    appendSelected(stringWriter);
    stringWriter.appendString(" from ");
    appendFrom(stringWriter);
  }

  private void appendSelected(JpqlStringWriter stringWriter) {
    for (int i = 0; i < selected.size(); i++) {
      if (i > 0) {
        stringWriter.appendString(", ");
      }
      writeThing(selected.get(i), stringWriter);
    }
  }

  private static void writeThing(Object thing, JpqlStringWriter stringWriter) {
    if (thing instanceof Operator) {
      ((Operator) thing).writeTo(stringWriter);
    } else {
      stringWriter.appendValue(thing);
    }
  }

  private void appendFrom(JpqlStringWriter stringWriter) {
    for (int i = 0; i < from.size(); i++) {
      if (i > 0) {
        stringWriter.appendString(", ");
      }
      stringWriter.appendValue(from.get(i));
      stringWriter.appendString(" ");
      stringWriter.appendString(aliases.get(i));
    }
  }
}
