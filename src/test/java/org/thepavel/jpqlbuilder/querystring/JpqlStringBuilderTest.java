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

package org.thepavel.jpqlbuilder.querystring;

import org.junit.Before;
import org.junit.Test;
import org.thepavel.jpqlbuilder.DummyOperator;
import org.thepavel.jpqlbuilder.JpqlBuilderContext;
import org.thepavel.jpqlbuilder.query.JoinType;
import org.thepavel.jpqlbuilder.query.SelectQuery;
import org.thepavel.jpqlbuilder.model.Company;
import org.thepavel.jpqlbuilder.model.Department;
import org.thepavel.jpqlbuilder.path.PathResolver;
import org.thepavel.jpqlbuilder.path.PathResolverList;
import org.thepavel.jpqlbuilder.query.JoinClause;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class JpqlStringBuilderTest {
  @Test
  public void empty() {
    JpqlStringBuilder builder = new JpqlStringBuilder(null, null);

    assertEquals("", builder.toString());
    assertEquals(new HashMap<String, Object>(), builder.getParameters());
  }

  @Test
  public void appendString() {
    JpqlStringBuilder builder = new JpqlStringBuilder(null, null);
    String random = UUID.randomUUID().toString();
    builder.appendString(random);

    assertEquals(random, builder.toString());
    assertEquals(new HashMap<String, Object>(), builder.getParameters());
  }

  @Test
  public void convertsEntityClassToEntityName() {
    JpqlStringBuilder builder = new JpqlStringBuilder(null, null);
    builder.appendValue(Company.class);

    assertEquals("test_Company", builder.toString());
    assertEquals(new HashMap<String, Object>(), builder.getParameters());
  }

  @Test
  public void looksUpPropertyPathInRootPathResolver() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    Company company = pathResolver.getPathSpecifier();

    PathResolverList roots = new PathResolverList();
    roots.add(pathResolver);

    JpqlStringBuilder builder = new JpqlStringBuilder(roots, null);
    builder.appendValue(company.getName());

    assertEquals("a.name", builder.toString());
    assertEquals(new HashMap<String, Object>(), builder.getParameters());
  }

  @Test
  public void looksUpPropertyPathInJoinedPathResolvers() {
    PathResolver<Department> pathResolver = new PathResolver<>(Department.class, "a", context);
    PathResolver<Company> joined = new PathResolver<>(Company.class, "b", context);
    Company company = joined.getPathSpecifier();

    PathResolverList roots = new PathResolverList();
    roots.add(pathResolver);

    PathResolverList joins = new PathResolverList();
    joins.add(joined);

    JpqlStringBuilder builder = new JpqlStringBuilder(roots, joins);
    builder.appendValue(company.getName());

    assertEquals("b.name", builder.toString());
    assertEquals(new HashMap<String, Object>(), builder.getParameters());
  }

  @Test
  public void addsParameterForUnrecognizedValue() {
    PathResolver<Department> pathResolver = new PathResolver<>(Department.class, "a", context);
    PathResolver<Company> joined = new PathResolver<>(Company.class, "b", context);

    PathResolverList roots = new PathResolverList();
    roots.add(pathResolver);

    PathResolverList joins = new PathResolverList();
    joins.add(joined);

    JpqlStringBuilder builder = new JpqlStringBuilder(roots, joins);
    Object value = new Object();
    builder.appendValue(value);

    assertEquals(":a", builder.toString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", value);
        }},
        builder.getParameters()
    );
  }

  @Test
  public void buildSelectQuery() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    Company company = pathResolver.getPathSpecifier();

    PathResolver<Department> joined = new PathResolver<>(Department.class, "b", context);
    Department department = joined.getPathSpecifier();

    PathResolverList roots = new PathResolverList();
    roots.add(pathResolver);

    PathResolverList joins = new PathResolverList();
    joins.add(joined);

    SelectQuery query = new SelectQuery();
    query.addSelected(company);
    query.addFrom(Company.class, "a");
    query.addJoin(new JoinClause("b", company.getDepartments(), JoinType.INNER));
    query.setWhere(DummyOperator.dummy("A"));
    query.addOrderBy(company.getName());
    query.setOrderDesc();
    query.addOrderBy(department.getName());

    JpqlStringBuilder builder = new JpqlStringBuilder(roots, joins);

    assertEquals(
        "select a from test_Company a join a.departments b where dummy(:a) order by a.name desc, b.name",
        builder.build(query)
    );
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", "A");
        }},
        builder.getParameters()
    );
  }

  @Test
  public void buildMultipleTimes() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    Company company = pathResolver.getPathSpecifier();

    PathResolverList roots = new PathResolverList();
    roots.add(pathResolver);

    JpqlStringBuilder builder = new JpqlStringBuilder(roots, null);

    SelectQuery query = new SelectQuery();
    query.addSelected(company);
    query.addFrom(Company.class, "a");

    assertEquals("select a from test_Company a", builder.build(query));
    assertEquals("select a from test_Company a", builder.build(query));
  }

  @Test
  public void returnsCopyOfParameters() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);

    PathResolverList roots = new PathResolverList();
    roots.add(pathResolver);

    JpqlStringBuilder builder = new JpqlStringBuilder(roots, new PathResolverList());

    SelectQuery query = new SelectQuery();
    query.setWhere(DummyOperator.dummy("A"));
    builder.build(query);

    Map<String, Object> params1 = builder.getParameters();
    Map<String, Object> params2 = builder.getParameters();

    Map<String, Object> expected = new HashMap<>();
    expected.put("a", "A");

    assertNotSame(params1, params2);
    assertEquals(expected, params1);
    assertEquals(expected, params2);
  }

  private JpqlBuilderContext context;

  @Before
  public void setup() {
    context = JpqlBuilderContext.defaultContext();
  }
}
