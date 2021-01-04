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
import org.thepavel.jpqlbuilder.JpqlQuery;
import org.thepavel.jpqlbuilder.Select;
import org.thepavel.jpqlbuilder.SelectBuilder;
import org.thepavel.jpqlbuilder.model.Company;
import org.thepavel.jpqlbuilder.model.Department;
import org.thepavel.jpqlbuilder.model.Status;
import org.thepavel.jpqlbuilder.operators.Operator;
import org.thepavel.jpqlbuilder.operators.Parentheses;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.functions.Functions.concat;
import static org.thepavel.jpqlbuilder.functions.Functions.lower;
import static org.thepavel.jpqlbuilder.functions.Functions.multi;
import static org.thepavel.jpqlbuilder.functions.Functions.upper;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class SelectMultipleThingsTest {
  @Test
  public void selectMultipleEntities() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Company c = builder.from(Company.class);
    Department d = builder.from(Department.class);
    Select select = builder.select(c, d);

    assertEquals("select a, b from test_Company a, test_Department b", select.getQueryString());
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void selectMultipleValues() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Company c = builder.from(Company.class);
    Department d = builder.from(Department.class);
    Select select = builder.select(c.getName(), d.getStatus());

    assertEquals("select a.name, b.status from test_Company a, test_Department b", select.getQueryString());
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void selectMultipleFunctions() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Company c = builder.from(Company.class);
    Department d = builder.from(Department.class);
    Select select = builder.select(lower(c.getName()), upper(d.getName()));

    assertEquals("select lower(a.name), upper(b.name) from test_Company a, test_Department b", select.getQueryString());
    assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void selectMixed() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();

    Company a = builder.from(Company.class);
    Company b = builder.from(Company.class);
    Department c = builder.from(Department.class);
    Department d = builder.join(a.getDepartments()).on(x -> $(x.getStatus()).isNot(Status.DELETED)).getPathSpecifier();

    Operator rank = new Parentheses(multi(a.getId(), 2));
    Operator name = concat(lower(a.getName()), upper(c.getName()));

    JpqlQuery select = builder
        .select(rank, name, a.getStatus(), a, b, c, d)
        .where(a.getStatus()).isNot(Status.DELETED)
        .and(c.getStatus()).isNot(Status.DELETED)
        .orderBy(name);

    String expected = "select (a.id * :a), concat(lower(a.name), upper(c.name)), a.status, a, b, c, d " +
        "from test_Company a, test_Company b, test_Department c " +
        "join a.departments d on d.status <> :b " +
        "where a.status <> :c and c.status <> :d " +
        "order by concat(lower(a.name), upper(c.name))";

    assertEquals(expected, select.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", 2);
          put("b", Status.DELETED);
          put("c", Status.DELETED);
          put("d", Status.DELETED);
        }},
        select.getParameters()
    );
  }
}
