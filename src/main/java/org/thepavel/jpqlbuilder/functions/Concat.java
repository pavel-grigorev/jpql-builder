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

import org.thepavel.jpqlbuilder.utils.CollectionUtils;
import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;

import java.util.List;

public class Concat extends JpqlFunction<String> {
  private final List<Object> parameters;

  public Concat(String... parameters) {
    this.parameters = CollectionUtils.toList(parameters);
  }

  @SafeVarargs
  public Concat(JpqlFunction<String>... parameters) {
    this.parameters = CollectionUtils.toList(parameters);
  }

  public Concat concat(JpqlFunction<String> nested) {
    parameters.add(nested);
    return this;
  }

  public Concat concat(String parameter) {
    parameters.add(parameter);
    return this;
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    stringWriter.appendString("concat(");
    appendParameters(stringWriter);
    stringWriter.appendString(")");
  }

  private void appendParameters(JpqlStringWriter stringWriter) {
    for (int i = 0; i < parameters.size(); i++) {
      if (i > 0) {
        stringWriter.appendString(", ");
      }
      writeOperand(parameters.get(i), stringWriter);
    }
  }
}
