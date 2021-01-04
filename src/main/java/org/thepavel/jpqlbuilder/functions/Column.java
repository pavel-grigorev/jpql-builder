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
import org.thepavel.jpqlbuilder.utils.EntityHelper;

public class Column<T> extends JpqlFunction<T> {
  private final String name;
  private final Object entity;

  public Column(String name, Object entity) {
    if (name == null || entity == null) {
      throw new NullPointerException();
    }
    if (name.indexOf('\'') != -1) {
      throw new IllegalArgumentException("single quote character is not allowed");
    }
    if (!EntityHelper.isProxiedEntity(entity)) {
      throw new IllegalArgumentException("not an entity");
    }
    this.name = name;
    this.entity = entity;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("column('");
    stringWriter.appendString(name);
    stringWriter.appendString("', ");
    writeOperand(entity, stringWriter);
    stringWriter.appendString(")");
  }
}
