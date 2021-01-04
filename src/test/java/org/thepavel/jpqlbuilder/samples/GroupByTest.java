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

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.functions.Functions.count;
import static org.thepavel.jpqlbuilder.functions.Functions.upper;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class GroupByTest {
  @Test
  public void groupBy() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Company c = builder.from(Company.class);
    Department d = builder.join(c.getDepartments()).getPathSpecifier();
    Select select = builder.select(c, count(d));

    String query = select.groupBy(c).getQueryString();

    String expected = "select a, count(b) from test_Company a join a.departments b group by a";

    assertEquals(expected, query);
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void multipleGroupBy() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Company c = builder.from(Company.class);
    Department d = builder.join(c.getDepartments()).getPathSpecifier();
    Select select = builder.select(c, count(d));

    String query = select.groupBy(c).groupBy(count(d)).getQueryString();

    String expected = "select a, count(b) from test_Company a join a.departments b group by a, count(b)";

    assertEquals(expected, query);
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void groupByAttribute() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Company c = builder.from(Company.class);
    Department d = builder.join(c.getDepartments()).getPathSpecifier();
    Select select = builder.select(c.getName(), count(d));

    String query = select.groupBy(c.getName()).getQueryString();

    String expected = "select a.name, count(b) from test_Company a join a.departments b group by a.name";

    assertEquals(expected, query);
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void groupByFunction() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Company c = builder.from(Company.class);
    Department d = builder.join(c.getDepartments()).getPathSpecifier();
    Select select = builder.select(upper(c.getName()), count(d));

    String query = select.groupBy(upper(c.getName())).getQueryString();

    String expected = "select upper(a.name), count(b) from test_Company a join a.departments b group by upper(a.name)";

    assertEquals(expected, query);
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void whereAndGroupByAndOrderBy() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Company c = builder.from(Company.class);
    Department d = builder.join(c.getDepartments()).on(x -> $(x.getStatus()).isNot(Status.DELETED)).getPathSpecifier();
    Select select = builder.select(c, count(d));

    String query = select
        .where(c.getStatus()).isNot(Status.DELETED)
        .groupBy(c)
        .orderBy(count(d))
        .getQueryString();

    String expected = "select a, count(b) from test_Company a " +
        "join a.departments b on b.status <> :a " +
        "where a.status <> :b " +
        "group by a " +
        "order by count(b)";

    assertEquals(expected, query);
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }
}
