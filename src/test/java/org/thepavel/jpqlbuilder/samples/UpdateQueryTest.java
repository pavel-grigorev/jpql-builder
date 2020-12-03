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
import org.thepavel.jpqlbuilder.JpqlBuilder;
import org.thepavel.jpqlbuilder.JpqlQuery;
import org.thepavel.jpqlbuilder.Update;
import org.thepavel.jpqlbuilder.UpdateBuilder;
import org.thepavel.jpqlbuilder.model.Company;
import org.thepavel.jpqlbuilder.model.Status;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.functions.Functions.concat;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.$;
import static org.thepavel.jpqlbuilder.operators.builders.UpdateBuilder.set;

public class UpdateQueryTest {
  @Test
  public void minimal() {
    UpdateBuilder builder = JpqlBuilder.updateBuilder();
    Company c = builder.entity(Company.class);

    Update query = builder.update(c);

    assertEquals("update test_Company a", query.getQueryString());
    assertEquals(new HashMap<String, Object>(), query.getParameters());
  }

  @Test
  public void oneUpdate() {
    UpdateBuilder builder = JpqlBuilder.updateBuilder();
    Company c = builder.entity(Company.class);

    Update query = builder.update(c).set(c.getStatus()).to(Status.ACTIVE);

    assertEquals("update test_Company a set a.status = :a", query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.ACTIVE);
        }},
        query.getParameters()
    );
  }

  @Test
  public void multipleUpdates() {
    UpdateBuilder builder = JpqlBuilder.updateBuilder();
    Company c = builder.entity(Company.class);

    Update query = builder
        .update(c)
        .set(c.getStatus()).to(Status.ACTIVE)
        .set(c.getName()).to("ABC");

    String expected = "update test_Company a set a.status = :a, a.name = :b";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.ACTIVE);
          put("b", "ABC");
        }},
        query.getParameters()
    );
  }

  @Test
  public void multipleUpdatesWhere() {
    UpdateBuilder builder = JpqlBuilder.updateBuilder();
    Company c = builder.entity(Company.class);

    JpqlQuery query = builder
        .update(c)
        .set(c.getStatus()).to(Status.ACTIVE)
        .set(c.getName()).to("ABC")
        .where(c.getStatus()).is(Status.DISABLED);

    String expected = "update test_Company a set a.status = :a, a.name = :b where a.status = :c";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.ACTIVE);
          put("b", "ABC");
          put("c", Status.DISABLED);
        }},
        query.getParameters()
    );
  }

  @Test
  public void dynamicQuery() {
    UpdateBuilder builder = JpqlBuilder.updateBuilder();
    Company c = builder.entity(Company.class);

    Update query = builder.update(c).set(c.getStatus()).to(Status.DELETED);
    Update.Where where = null;

    for (Status status : Arrays.asList(Status.DISABLED, Status.SUSPENDED)) {
      if (where == null) {
        where = query.where(c.getStatus()).is(status);
      } else {
        where.or(c.getStatus()).is(status);
      }
    }

    query.set(c.getName()).to(concat("DELETED-", c.getName()));

    String expected = "update test_Company a set a.status = :a, a.name = concat(:b, a.name) " +
        "where a.status = :c or a.status = :d";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", "DELETED-");
          put("c", Status.DISABLED);
          put("d", Status.SUSPENDED);
        }},
        query.getParameters()
    );
  }

  @Test
  public void oneLinerMinimal() {
    JpqlQuery query = JpqlBuilder
        .updateBuilder()
        .update(Company.class)
        .set(c -> set(c.getStatus()).to(Status.DELETED));

    String expected = "update test_Company a set a.status = :a";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        query.getParameters()
    );
  }

  @Test
  public void oneLinerWhere() {
    JpqlQuery query = JpqlBuilder
        .updateBuilder()
        .update(Company.class)
        .set(c -> set(c.getStatus()).to(Status.DELETED).set(c.getName()).to("DELETED"))
        .where(c -> $(c.getName()).is("Google"));

    String expected = "update test_Company a set a.status = :a, a.name = :b where a.name = :c";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", "DELETED");
          put("c", "Google");
        }},
        query.getParameters()
    );
  }
}
