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
import org.thepavel.jpqlbuilder.DummyJpqlStringWriter;
import org.thepavel.jpqlbuilder.model.Company;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class JoinClauseTest {
  @Test
  public void innerJoin() {
    JoinClause join = new JoinClause("a", "thing", JoinType.INNER);

    assertEquals(" join thing a", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void innerJoinOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.INNER);
    join.setOnClause($(1).is(2));

    assertEquals(" join thing a on 1 = 2", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void leftJoin() {
    JoinClause join = new JoinClause("a", "thing", JoinType.LEFT);

    assertEquals(" left join thing a", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void leftJoinOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.LEFT);
    join.setOnClause($(1).is(2));

    assertEquals(" left join thing a on 1 = 2", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void joinFetch() {
    JoinClause join = new JoinClause("a", "thing", JoinType.FETCH);

    assertEquals(" join fetch thing", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void joinFetchOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.FETCH);
    join.setOnClause($(1).is(2));

    assertEquals(" join fetch thing on 1 = 2", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void joinFetchWithAlias() {
    JoinClause join = new JoinClause("a", "thing", JoinType.FETCH_WITH_ALIAS);

    assertEquals(" join fetch thing a", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void joinFetchWithAliasOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.FETCH_WITH_ALIAS);
    join.setOnClause($(1).is(2));

    assertEquals(" join fetch thing a on 1 = 2", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void joinAs() {
    JoinClause join = new JoinClause("a", "thing", JoinType.INNER);
    join.setTreatAsType(Company.class);

    assertEquals(" join treat(thing as Company) a", DummyJpqlStringWriter.asString(join));
  }

  @Test
  public void joinAsOn() {
    JoinClause join = new JoinClause("a", "thing", JoinType.INNER);
    join.setTreatAsType(Company.class);
    join.setOnClause($(1).is(2));

    assertEquals(" join treat(thing as Company) a on 1 = 2", DummyJpqlStringWriter.asString(join));
  }
}
