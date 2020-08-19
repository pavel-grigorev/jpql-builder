package org.test.querystring;

import org.junit.Test;
import org.test.entities.Advertiser;
import org.test.entities.Campaign;
import org.test.path.PathResolver;
import org.test.path.PathResolverList;
import org.test.query.JoinClause;
import org.test.query.JoinType;
import org.test.query.SelectQuery;

import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
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
    builder.appendValue(Advertiser.class);

    assertEquals("test$Advertiser", builder.toString());
    assertEquals(new HashMap<String, Object>(), builder.getParameters());
  }

  @Test
  public void looksUpPropertyPathInRootPathResolver() {
    PathResolver<Advertiser> pathResolver = new PathResolver<>(Advertiser.class, "a");
    Advertiser advertiser = pathResolver.getPathSpecifier();

    JpqlStringBuilder builder = new JpqlStringBuilder(pathResolver, null);
    builder.appendValue(advertiser.getName());

    assertEquals("a.name", builder.toString());
    assertEquals(new HashMap<String, Object>(), builder.getParameters());
  }

  @Test
  public void looksUpPropertyPathInJoinedPathResolvers() {
    PathResolver<Advertiser> pathResolver = new PathResolver<>(Advertiser.class, "a");
    PathResolver<Campaign> joined = new PathResolver<>(Campaign.class, "b");
    Campaign campaign = joined.getPathSpecifier();

    PathResolverList joins = new PathResolverList();
    joins.add(joined);

    JpqlStringBuilder builder = new JpqlStringBuilder(pathResolver, joins);
    builder.appendValue(campaign.getName());

    assertEquals("b.name", builder.toString());
    assertEquals(new HashMap<String, Object>(), builder.getParameters());
  }

  @Test
  public void addsParameterForUnrecognizedValue() {
    PathResolver<Advertiser> pathResolver = new PathResolver<>(Advertiser.class, "a");
    PathResolver<Campaign> joined = new PathResolver<>(Campaign.class, "b");

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
    PathResolver<Advertiser> pathResolver = new PathResolver<>(Advertiser.class, "a");
    Advertiser advertiser = pathResolver.getPathSpecifier();

    PathResolver<Campaign> joined = new PathResolver<>(Campaign.class, "b");
    Campaign campaign = joined.getPathSpecifier();

    PathResolverList joins = new PathResolverList();
    joins.add(joined);

    SelectQuery query = new SelectQuery("a", Advertiser.class);
    query.addJoin(new JoinClause("b", advertiser.getCampaigns(), JoinType.INNER));
    query.setWhere(dummy("A"));
    query.addOrderBy(advertiser.getName());
    query.setOrderDesc();
    query.addOrderBy(campaign.getName());

    JpqlStringBuilder builder = new JpqlStringBuilder(pathResolver, joins);

    assertEquals(
        "select a from test$Advertiser a join a.campaigns b where dummy(:a) order by a.name desc, b.name",
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
    SelectQuery query = new SelectQuery("a", Advertiser.class);
    JpqlStringBuilder builder = new JpqlStringBuilder(null, null);
    assertEquals("select a from test$Advertiser a", builder.build(query));
    assertEquals("select a from test$Advertiser a", builder.build(query));
  }
}
