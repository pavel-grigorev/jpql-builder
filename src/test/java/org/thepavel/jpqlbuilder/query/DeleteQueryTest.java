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

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.DummyJpqlStringWriter.asString;
import static org.thepavel.jpqlbuilder.DummyOperator.dummy;

public class DeleteQueryTest {
  @Test
  public void minimal() {
    DeleteQuery delete = new DeleteQuery();
    delete.setFrom(Company.class, "a");

    assertEquals("delete from Company a", asString(delete));
  }

  @Test
  public void where() {
    DeleteQuery delete = new DeleteQuery();
    delete.setFrom(Company.class, "a");
    delete.setWhere(dummy("A"));

    assertEquals("delete from Company a where dummy(A)", asString(delete));
  }
}
