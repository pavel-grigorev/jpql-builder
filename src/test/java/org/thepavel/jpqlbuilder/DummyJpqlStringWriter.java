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

package org.thepavel.jpqlbuilder;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.springframework.aop.support.AopUtils;
import org.thepavel.jpqlbuilder.operators.Operator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DummyJpqlStringWriter implements JpqlStringWriter {
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private final StringBuilder builder = new StringBuilder();

  @Override
  public void appendString(String string) {
    builder.append(string);
  }

  @Override
  public void appendValue(Object value) {
    if (value instanceof Class) {
      builder.append(((Class<?>) value).getSimpleName());
      return;
    }
    if (value instanceof Date) {
      builder.append(dateFormat.format((Date) value));
      return;
    }
    if (AopUtils.isAopProxy(value)) {
      builder.append(AopUtils.getTargetClass(value).getSimpleName());
      return;
    }
    builder.append(value);
  }

  @Override
  public String toString() {
    return builder.toString();
  }

  public static String asString(Operator operator) {
    JpqlStringWriter stringWriter = new DummyJpqlStringWriter();
    operator.writeTo(stringWriter);
    return stringWriter.toString();
  }
}
