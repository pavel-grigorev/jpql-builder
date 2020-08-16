package org.test.jpqlbuildertests;

import org.junit.Assert;
import org.junit.Test;
import org.test.JpqlBuilder;
import org.test.Where;
import org.test.entities.AdGroup;
import org.test.entities.AdGroupBid;
import org.test.entities.Advertiser;
import org.test.entities.Campaign;
import org.test.entities.Publisher;
import org.test.entities.Status;
import org.test.operators.builders.ExpressionChain;

import java.util.Arrays;
import java.util.HashMap;

import static org.test.functions.Functions.lower;
import static org.test.functions.Functions.upper;
import static org.test.operators.builders.OperatorBuilder.$;
import static org.test.operators.builders.OperatorBuilder.not;
import static org.test.operators.builders.StringOperatorBuilder.$;

public class JpqlBuilderTest {
  @Test
  public void testMinimal() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    Assert.assertEquals("select a from test$AdGroup a", select.getQueryString());
    Assert.assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testOrderBy() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a order by lower(a.name) desc, a.id",
        select.orderBy(lower(adGroup.getName())).desc().orderBy(adGroup.getId()).getQueryString()
    );
    Assert.assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testOrderByWithNullsOrdering() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a order by upper(a.name) desc nulls first, a.id asc nulls last, a.campaign",
        select
            .orderBy(upper(adGroup.getName())).desc().nullsFirst()
            .orderBy(adGroup.getId()).asc().nullsLast()
            .orderBy(adGroup.getCampaign())
            .getQueryString()
    );
    Assert.assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testWhereAndOrderBy() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a where a.status <> :a order by a.name desc",
        select
            .where(adGroup.getStatus()).isNot(Status.DELETED)
            .orderBy(adGroup.getName()).desc().getQueryString()
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testWhereString() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a where a.name like :a",
        select.where(adGroup.getName()).like("%test%").getQueryString()
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", "%test%");
        }},
        select.getParameters()
    );
  }

  @Test
  public void testWhereExpression() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    String query = select
        .where(
            $(adGroup.getName()).is("Test")
                .or(adGroup.getStatus()).is(Status.ACTIVE)
        )
        .and(adGroup.getId()).isNot(1L)
        .getQueryString();
    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "where (a.name = :a or a.status = :b) " +
            "and a.id <> :c",
        query
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", "Test");
          put("b", Status.ACTIVE);
          put("c", 1L);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testWhere() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    String query = select
        .where(adGroup.getId()).isNot(123456L)
        .and(adGroup.getStatus()).isNot(Status.DELETED)
        .and(adGroup.getCampaign().getStatus()).isNot(Status.DELETED)
        .and(adGroup.getCampaign().getAdvertiser().getStatus()).isNot(Status.DELETED)
        .and(adGroup.getCampaign().getName()).like("%test%")
        .or(adGroup.getName()).isNull()
        .or(adGroup.getCampaign().getId()).isNotNull()
        .and(
            $(adGroup.getCampaign().getName()).notLike("A")
                .or(adGroup.getCampaign().getAdvertiser().getName()).like("10\\%", "\\")
                .or(not(
                    $(adGroup.getStatus()).is(Status.ACTIVE)
                        .and(adGroup.getName()).like("B")
                ))
                .and(adGroup.getId()).between(10L, 20L)
                .and(
                    $(adGroup.getStatus()).in(Status.ACTIVE, Status.SUSPENDED)
                    .or(adGroup.getStatus()).notIn(Status.DELETED, Status.DISABLED)
                )
        )
        .or(
            $(adGroup.getId()).greaterThanOrEqual(0L)
            .and(adGroup.getId()).lessThanOrEqual(100L)
        )
        .getQueryString();
    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "where a.id <> :a " +
            "and a.status <> :b " +
            "and a.campaign.status <> :c " +
            "and a.campaign.advertiser.status <> :d " +
            "and a.campaign.name like :e " +
            "or a.name is null " +
            "or a.campaign.id is not null " +
            "and (" +
              "a.campaign.name not like :f " +
              "or a.campaign.advertiser.name like :g escape :h " +
              "or (not (a.status = :i and a.name like :j)) " +
              "and a.id between :k and :l " +
              "and (a.status in :m or a.status not in :n)" +
            ") " +
            "or (a.id >= :o and a.id <= :p)",
        query
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", 123456L);
          put("b", Status.DELETED);
          put("c", Status.DELETED);
          put("d", Status.DELETED);
          put("e", "%test%");
          put("f", "A");
          put("g", "10\\%");
          put("h", "\\");
          put("i", Status.ACTIVE);
          put("j", "B");
          put("k", 10L);
          put("l", 20L);
          put("m", Arrays.asList(Status.ACTIVE, Status.SUSPENDED));
          put("n", Arrays.asList(Status.DELETED, Status.DISABLED));
          put("o", 0L);
          put("p", 100L);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testEntityJoins() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Campaign campaign = select.join(adGroup.getCampaign()).getPathSpecifier();
    Advertiser advertiser = select.join(campaign.getAdvertiser()).getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "join a.campaign b " +
            "join b.advertiser c " +
            "where a.status <> :a " +
            "and b.status <> :b " +
            "and c.status <> :c " +
            "order by c.name, b.name, a.name",
        select
            .where(adGroup.getStatus()).isNot(Status.DELETED)
            .and(campaign.getStatus()).isNot(Status.DELETED)
            .and(advertiser.getStatus()).isNot(Status.DELETED)
            .orderBy(advertiser.getName())
            .orderBy(campaign.getName())
            .orderBy(adGroup.getName())
            .getQueryString()
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
          put("c", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testCollectionJoins() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Campaign campaign = select.join(adGroup.getCampaign()).getPathSpecifier();
    Advertiser advertiser = select.join(campaign.getAdvertiser()).getPathSpecifier();
    AdGroupBid bid = select.join(adGroup.getBids()).getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "join a.campaign b " +
            "join b.advertiser c " +
            "join a.bids d " +
            "where a.status <> :a " +
            "and b.status <> :b " +
            "and c.status <> :c " +
            "and d.active = :d " +
            "order by c.name, b.name, a.name",
        select
            .where(adGroup.getStatus()).isNot(Status.DELETED)
            .and(campaign.getStatus()).isNot(Status.DELETED)
            .and(advertiser.getStatus()).isNot(Status.DELETED)
            .and(bid.getActive()).is(Boolean.TRUE)
            .orderBy(advertiser.getName())
            .orderBy(campaign.getName())
            .orderBy(adGroup.getName())
            .getQueryString()
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
          put("c", Status.DELETED);
          put("d", Boolean.TRUE);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testLeftJoin() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Campaign campaign = select.leftJoin(adGroup.getCampaign()).getPathSpecifier();
    Advertiser advertiser = select.leftJoin(campaign.getAdvertiser()).getPathSpecifier();
    AdGroupBid bid = select.leftJoin(adGroup.getBids()).getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "left join a.campaign b " +
            "left join b.advertiser c " +
            "left join a.bids d " +
            "where a.status <> :a " +
            "and b.status <> :b " +
            "and c.status <> :c " +
            "and d.active = :d " +
            "order by c.name, b.name, a.name",
        select
            .where(adGroup.getStatus()).isNot(Status.DELETED)
            .and(campaign.getStatus()).isNot(Status.DELETED)
            .and(advertiser.getStatus()).isNot(Status.DELETED)
            .and(bid.getActive()).is(Boolean.TRUE)
            .orderBy(advertiser.getName())
            .orderBy(campaign.getName())
            .orderBy(adGroup.getName())
            .getQueryString()
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
          put("c", Status.DELETED);
          put("d", Boolean.TRUE);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testJoinFetch() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    select.joinFetch(adGroup.getCampaign());
    select.joinFetch(adGroup.getBids());
    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "join fetch a.campaign " +
            "join fetch a.bids " +
            "where a.status <> :a " +
            "order by a.name",
        select
            .where(adGroup.getStatus()).isNot(Status.DELETED)
            .orderBy(adGroup.getName())
            .getQueryString()
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testJoinFetchWithAlias() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Campaign campaign = select.joinFetchWithAlias(adGroup.getCampaign()).getPathSpecifier();
    AdGroupBid bid = select.joinFetchWithAlias(adGroup.getBids()).getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "join fetch a.campaign b " +
            "join fetch a.bids c " +
            "where a.status <> :a " +
            "and b.status <> :b " +
            "order by c.value",
        select
            .where(adGroup.getStatus()).isNot(Status.DELETED)
            .and(campaign.getStatus()).isNot(Status.DELETED)
            .orderBy(bid.getValue())
            .getQueryString()
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testClassJoin() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Publisher publisher = select.join(Publisher.class).getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "join test$Publisher b " +
            "where a.status = b.status",
        select
            .where(adGroup.getStatus()).is(publisher.getStatus())
            .getQueryString()
    );
    Assert.assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testClassLeftJoin() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Publisher publisher = select.leftJoin(Publisher.class).getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "left join test$Publisher b " +
            "where a.status = b.status",
        select
            .where(adGroup.getStatus()).is(publisher.getStatus())
            .getQueryString()
    );
    Assert.assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testCollectionJoinOn() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    AdGroupBid bid = select
        .join(adGroup.getBids())
        .on(b -> $(b.getActive()).is(Boolean.TRUE))
        .getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "join a.bids b on b.active = :a " +
            "where a.status <> :b " +
            "order by b.value desc",
        select
            .where(adGroup.getStatus()).isNot(Status.DELETED)
            .orderBy(bid.getValue()).desc()
            .getQueryString()
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", Boolean.TRUE);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testClassJoinOn() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Publisher publisher = select
        .join(Publisher.class)
        .on(p -> $(p.getName()).is(adGroup.getName()))
        .getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "join test$Publisher b on b.name = a.name " +
            "where a.status <> :a " +
            "and b.status <> :b " +
            "order by b.name desc",
        select
            .where(adGroup.getStatus()).isNot(Status.DELETED)
            .and(publisher.getStatus()).isNot(Status.DELETED)
            .orderBy(publisher.getName()).desc()
            .getQueryString()
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", Status.DELETED);
        }},
        select.getParameters()
    );
  }

  @Test
  public void testLowerUpper() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Publisher publisher = select
        .join(Publisher.class)
        .on(p -> $(lower(p.getName())).like(lower(adGroup.getName())))
        .getPathSpecifier();
    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "join test$Publisher b on lower(b.name) like lower(a.name) " +
            "where upper(a.name) like upper(:a) " +
            "or lower(a.name) like lower(b.name) " +
            "and upper(a.name) not like upper(b.name)",
        select
            .where(upper(adGroup.getName())).like(upper("%test%"))
            .or(lower(adGroup.getName())).like(lower(publisher.getName()))
            .and(upper(adGroup.getName())).notLike(upper(publisher.getName()))
            .getQueryString()
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", "%test%");
        }},
        select.getParameters()
    );
  }

  @Test
  public void testDynamicQuery() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();

    Where<AdGroup> where = select.where(adGroup.getStatus()).isNot(Status.DELETED);
    where.and(adGroup.getName()).like("%test%");

    ExpressionChain idFilter = $(adGroup.getId()).is(1L);
    idFilter.or(adGroup.getId()).is(2L);
    idFilter.or(adGroup.getId()).is(3L);

    where.and(idFilter);

    where.orderBy(adGroup.getId()).asc();
    select.orderBy(adGroup.getName()).desc();

    Assert.assertEquals(
        "select a from test$AdGroup a " +
            "where a.status <> :a " +
            "and a.name like :b " +
            "and (a.id = :c or a.id = :d or a.id = :e) " +
            "order by a.id asc, a.name desc",
        select.getQueryString()
    );
    Assert.assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", "%test%");
          put("c", 1L);
          put("d", 2L);
          put("e", 3L);
        }},
        select.getParameters()
    );
  }
}
