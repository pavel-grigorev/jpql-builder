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

import org.junit.Assert;
import org.junit.Test;
import org.thepavel.jpqlbuilder.DummyJpqlStringWriter;
import org.thepavel.jpqlbuilder.DummyOperator;

import static org.junit.Assert.assertEquals;

public class WhereClauseTest {
  @Test
  public void empty() {
    Assert.assertEquals("", DummyJpqlStringWriter.asString(new WhereClause()));
  }

  @Test
  public void notEmpty() {
    WhereClause where = new WhereClause();
    where.setOperator(DummyOperator.dummy("A"));

    Assert.assertEquals(" where dummy(A)", DummyJpqlStringWriter.asString(where));
  }
}
