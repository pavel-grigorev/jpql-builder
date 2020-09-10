package org.test.querystring;

import org.junit.Before;
import org.junit.Test;
import org.test.JpqlBuilderContext;
import org.test.model.Company;
import org.test.model.Department;
import org.test.path.PathResolver;
import org.test.path.PathResolverList;
import org.test.query.JoinClause;
import org.test.query.JoinType;
import org.test.query.SelectQuery;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.test.DummyOperator.dummy;

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

    JpqlStringBuilder builder = new JpqlStringBuilder(pathResolver, null);
    builder.appendValue(company.getName());

    assertEquals("a.name", builder.toString());
    assertEquals(new HashMap<String, Object>(), builder.getParameters());
  }

  @Test
  public void looksUpPropertyPathInJoinedPathResolvers() {
    PathResolver<Department> pathResolver = new PathResolver<>(Department.class, "a", context);
    PathResolver<Company> joined = new PathResolver<>(Company.class, "b", context);
    Company company = joined.getPathSpecifier();

    PathResolverList joins = new PathResolverList();
    joins.add(joined);

    JpqlStringBuilder builder = new JpqlStringBuilder(pathResolver, joins);
    builder.appendValue(company.getName());

    assertEquals("b.name", builder.toString());
    assertEquals(new HashMap<String, Object>(), builder.getParameters());
  }

  @Test
  public void addsParameterForUnrecognizedValue() {
    PathResolver<Department> pathResolver = new PathResolver<>(Department.class, "a", context);
    PathResolver<Company> joined = new PathResolver<>(Company.class, "b", context);

    PathResolverList joins = new PathResolverList();
    joins.add(joined);

    JpqlStringBuilder builder = new JpqlStringBuilder(pathResolver, joins);
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

    PathResolverList joins = new PathResolverList();
    joins.add(joined);

    SelectQuery query = new SelectQuery("a", Company.class);
    query.addJoin(new JoinClause("b", company.getDepartments(), JoinType.INNER));
    query.setWhere(dummy("A"));
    query.addOrderBy(company.getName());
    query.setOrderDesc();
    query.addOrderBy(department.getName());

    JpqlStringBuilder builder = new JpqlStringBuilder(pathResolver, joins);

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
    SelectQuery query = new SelectQuery("a", Company.class);
    JpqlStringBuilder builder = new JpqlStringBuilder(null, null);
    assertEquals("select a from test_Company a", builder.build(query));
    assertEquals("select a from test_Company a", builder.build(query));
  }

  @Test
  public void returnsCopyOfParameters() {
    SelectQuery query = new SelectQuery("a", Company.class);
    query.setWhere(dummy("A"));

    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    JpqlStringBuilder builder = new JpqlStringBuilder(pathResolver, new PathResolverList());
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
