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

package org.thepavel.jpqlbuilder.query;

import org.junit.Test;
import org.thepavel.jpqlbuilder.model.Company;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.DummyJpqlStringWriter.asString;

public class UpdateClauseTest {
  @Test
  public void minimal() {
    UpdateClause update = new UpdateClause();
    update.setEntityClass(Company.class, "a");

    assertEquals("update Company a", asString(update));
  }

  @Test
  public void oneUpdate() {
    UpdateClause update = new UpdateClause();
    update.setEntityClass(Company.class, "a");

    Map<Object, Object> updates = new LinkedHashMap<>();
    update.setUpdates(updates);
    updates.put("a.name", "x");

    assertEquals("update Company a set a.name = x", asString(update));
  }

  @Test
  public void multipleUpdates() {
    UpdateClause update = new UpdateClause();
    update.setEntityClass(Company.class, "a");

    Map<Object, Object> updates = new LinkedHashMap<>();
    update.setUpdates(updates);
    updates.put("a.name", "x");
    updates.put("a.status", "DELETED");

    assertEquals("update Company a set a.name = x, a.status = DELETED", asString(update));
  }
}
