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

package org.thepavel.jpqlbuilder.samples;

import org.junit.Test;
import org.thepavel.jpqlbuilder.Delete;
import org.thepavel.jpqlbuilder.DeleteBuilder;
import org.thepavel.jpqlbuilder.JpqlBuilder;
import org.thepavel.jpqlbuilder.JpqlQuery;
import org.thepavel.jpqlbuilder.model.Company;
import org.thepavel.jpqlbuilder.model.Status;
import org.thepavel.jpqlbuilder.operators.builders.ExpressionChain;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.functions.Functions.lower;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class DeleteQueryTest {
  @Test
  public void minimal() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();
    Company c = builder.from(Company.class);
    Delete delete = builder.delete(c);

    assertEquals("delete from test_Company a", delete.getQueryString());
    assertEquals(new HashMap<String, Object>(), delete.getParameters());
  }

  @Test
  public void whereProperty() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();
    Company c = builder.from(Company.class);

    JpqlQuery query = builder.delete(c).where(c.getStatus()).isNot(Status.DELETED);

    String expected = "delete from test_Company a where a.status <> :a";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        query.getParameters()
    );
  }

  @Test
  public void whereFunction() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();
    Company c = builder.from(Company.class);

    JpqlQuery query = builder
        .delete(c)
        .where(lower(c.getName())).like("%abc%")
        .and(c.getStatus()).isNot(Status.DELETED);

    String expected = "delete from test_Company a " +
        "where lower(a.name) like :a " +
        "and a.status <> :b";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "%abc%");
          put("b", Status.DELETED);
        }},
        query.getParameters()
    );
  }

  @Test
  public void whereCollection() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();
    Company c = builder.from(Company.class);

    JpqlQuery query = builder
        .delete(c)
        .where(c.getDepartments()).isNotEmpty()
        .and(c.getStatus()).isNot(Status.DELETED);

    String expected = "delete from test_Company a " +
        "where a.departments is not empty " +
        "and a.status <> :a";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        query.getParameters()
    );
  }

  @Test
  public void whereExpression() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();
    Company c = builder.from(Company.class);

    ExpressionChain statusNotDeleted = $(c.getStatus()).isNot(Status.DELETED);

    JpqlQuery query = builder.delete(c).where(statusNotDeleted);

    String expected = "delete from test_Company a where (a.status <> :a)";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        query.getParameters()
    );
  }

  @Test
  public void dynamicWhere() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();
    Company c = builder.from(Company.class);
    Delete query = builder.delete(c);
    Delete.Where where = null;

    for (Status status : Arrays.asList(Status.DELETED, Status.DISABLED)) {
      if (where == null) {
        where = query.where(c.getStatus()).isNot(status);
      } else {
        where.and(c.getStatus()).isNot(status);
      }
    }

    String expected = "delete from test_Company a where a.status <> :a and a.status <> :b";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DISABLED);
        }},
        query.getParameters()
    );
  }

  @Test
  public void dynamicExpression() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();
    Company c = builder.from(Company.class);
    ExpressionChain condition = null;

    for (Status status : Arrays.asList(Status.DELETED, Status.DISABLED)) {
      if (condition == null) {
        condition = $(c.getStatus()).isNot(status);
      } else {
        condition.and(c.getStatus()).isNot(status);
      }
    }

    JpqlQuery query = builder.delete(c).where(condition);

    String expected = "delete from test_Company a where (a.status <> :a and a.status <> :b)";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DISABLED);
        }},
        query.getParameters()
    );
  }

  @Test
  public void oneLinerMinimal() {
    JpqlQuery query = JpqlBuilder.deleteBuilder().delete(Company.class);

    assertEquals("delete from test_Company a", query.getQueryString());
    assertEquals(new HashMap<String, Object>(), query.getParameters());
  }

  @Test
  public void oneLinerWhere() {
    JpqlQuery query = JpqlBuilder
        .deleteBuilder()
        .delete(Company.class)
        .where(c -> $(c.getStatus()).is(Status.DELETED));

    String expected = "delete from test_Company a where a.status = :a";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        query.getParameters()
    );
  }
}
