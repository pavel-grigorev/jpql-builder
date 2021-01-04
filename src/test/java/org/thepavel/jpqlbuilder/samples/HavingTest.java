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
import org.thepavel.jpqlbuilder.JpqlBuilder;
import org.thepavel.jpqlbuilder.Select;
import org.thepavel.jpqlbuilder.SelectBuilder;
import org.thepavel.jpqlbuilder.model.Company;
import org.thepavel.jpqlbuilder.model.Department;
import org.thepavel.jpqlbuilder.model.Status;
import org.thepavel.jpqlbuilder.operators.builders.ExpressionChain;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.functions.Functions.count;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class HavingTest {
  @Test
  public void havingFunction() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Company c = builder.from(Company.class);
    Department d = builder.join(c.getDepartments()).getPathSpecifier();
    Select select = builder.select(c, count(d));

    String query = select.groupBy(c).having(count(d)).greaterThan(5).getQueryString();

    String expected = "select a, count(b) from test_Company a join a.departments b group by a having count(b) > :a";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 5);
        }},
        select.getParameters()
    );
  }

  @Test
  public void havingAttribute() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Company c = builder.from(Company.class);
    Department d = builder.join(c.getDepartments()).getPathSpecifier();
    Select select = builder.select(c, count(d));

    String query = select
        .groupBy(c)
        .having(d.getStatus()).isNot(Status.DELETED)
        .getQueryString();

    String expected = "select a, count(b) from test_Company a join a.departments b group by a having b.status <> :a";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void havingCollectionAttribute() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Company c = builder.from(Company.class);
    Department d = builder.join(c.getDepartments()).getPathSpecifier();
    Select select = builder.select(c, count(d));

    String query = select
        .groupBy(c)
        .having(d.getEmployees()).isNotEmpty()
        .getQueryString();

    String expected = "select a, count(b) from test_Company a join a.departments b " +
        "group by a having b.employees is not empty";

    assertEquals(expected, query);
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void havingExpression() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Company c = builder.from(Company.class);
    Department d = builder.join(c.getDepartments()).getPathSpecifier();
    Select select = builder.select(c, count(d));

    ExpressionChain count = $(count(d)).greaterThan(5);

    String query = select
        .groupBy(c)
        .having(count)
        .and(
            $(d.getStatus()).isNot(Status.DELETED)
            .or(d.getEmployees()).isNotEmpty()
        )
        .getQueryString();

    String expected = "select a, count(b) from test_Company a join a.departments b " +
        "group by a having (count(b) > :a) and (b.status <> :b or b.employees is not empty)";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 5);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }
}
