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

package org.thepavel.jpqlbuilder.samples;

import org.junit.Test;
import org.thepavel.jpqlbuilder.Select;
import org.thepavel.jpqlbuilder.SelectBuilder;
import org.thepavel.jpqlbuilder.model.Employee;
import org.thepavel.jpqlbuilder.JpqlBuilder;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class MinimalTest {
  @Test
  public void minimal() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Select select = builder.select(builder.from(Employee.class));

    assertEquals("select a from test_Employee a", select.getQueryString());
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }
}
