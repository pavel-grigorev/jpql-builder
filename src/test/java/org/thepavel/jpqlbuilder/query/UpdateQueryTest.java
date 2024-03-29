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

import org.junit.Test;
import org.thepavel.jpqlbuilder.model.Company;
import org.thepavel.jpqlbuilder.utils.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.DummyJpqlStringWriter.asString;
import static org.thepavel.jpqlbuilder.DummyOperator.dummy;

public class UpdateQueryTest {
  @Test
  public void minimal() {
    UpdateQuery update = new UpdateQuery();
    update.setEntityClass(Company.class, "a");

    assertEquals("update Company a", asString(update));
  }

  @Test
  public void where() {
    UpdateQuery update = new UpdateQuery();
    update.setEntityClass(Company.class, "a");
    update.setWhere(dummy("A"));

    assertEquals("update Company a where dummy(A)", asString(update));
  }

  @Test
  public void oneUpdate() {
    UpdateQuery update = new UpdateQuery();
    update.setEntityClass(Company.class, "a");

    List<Pair<Object, Object>> updates = new ArrayList<>();
    update.setUpdates(updates);
    updates.add(Pair.of("a.name", "x"));

    assertEquals("update Company a set a.name = x", asString(update));
  }

  @Test
  public void oneUpdateWhere() {
    UpdateQuery update = new UpdateQuery();
    update.setEntityClass(Company.class, "a");

    List<Pair<Object, Object>> updates = new ArrayList<>();
    update.setUpdates(updates);
    updates.add(Pair.of("a.name", "x"));

    update.setWhere(dummy("A"));

    assertEquals("update Company a set a.name = x where dummy(A)", asString(update));
  }

  @Test
  public void multipleUpdates() {
    UpdateQuery update = new UpdateQuery();
    update.setEntityClass(Company.class, "a");

    List<Pair<Object, Object>> updates = new ArrayList<>();
    update.setUpdates(updates);
    updates.add(Pair.of("a.name", "x"));
    updates.add(Pair.of("a.status", "DELETED"));

    assertEquals("update Company a set a.name = x, a.status = DELETED", asString(update));
  }

  @Test
  public void multipleUpdatesWhere() {
    UpdateQuery update = new UpdateQuery();
    update.setEntityClass(Company.class, "a");

    List<Pair<Object, Object>> updates = new ArrayList<>();
    update.setUpdates(updates);
    updates.add(Pair.of("a.name", "x"));
    updates.add(Pair.of("a.status", "DELETED"));

    update.setWhere(dummy("A"));

    assertEquals("update Company a set a.name = x, a.status = DELETED where dummy(A)", asString(update));
  }
}
