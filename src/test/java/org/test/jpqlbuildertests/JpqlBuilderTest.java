package org.test.jpqlbuildertests;

import org.junit.Assert;
import org.junit.Test;
import org.test.JpqlBuilder;
import org.test.entities.AdGroup;
import org.test.entities.Status;

import java.util.Arrays;
import java.util.HashMap;

import static org.test.operators.builders.OperatorBuilder.$;
import static org.test.operators.builders.OperatorBuilder.not;
import static org.test.operators.builders.StringOperatorBuilder.$;

public class JpqlBuilderTest {
  @Test
  public void testMinimal() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    Assert.assertEquals("select e from test$AdGroup e", select.build());
    Assert.assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testOrderBy() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    String query = select.orderBy(adGroup.getName(), adGroup.getId()).build();
    Assert.assertEquals("select e from test$AdGroup e order by e.name, e.id", query);
    Assert.assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testOrderByNullsFirst() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Assert.assertEquals(
        "select e from test$AdGroup e order by e.name nulls first",
        select.orderBy(adGroup.getName()).nullsFirst().build()
    );
    Assert.assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testOrderByNullsLast() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    Assert.assertEquals(
        "select e from test$AdGroup e order by e.name nulls last",
        select.orderBy(adGroup.getName()).nullsLast().build()
    );
    Assert.assertEquals(new HashMap<String, Object>(), select.getParameters());
  }

  @Test
  public void testWhereAndOrderBy() {
    JpqlBuilder<AdGroup> select = JpqlBuilder.select(AdGroup.class);
    AdGroup adGroup = select.getPathSpecifier();
    String query = select
        .where(adGroup.getStatus()).isNot(Status.DELETED)
        .orderBy(adGroup.getName()).build();
    Assert.assertEquals("select e from test$AdGroup e where e.status <> :a order by e.name", query);
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
    String query = select.where(adGroup.getName()).like("%test%").build();
    Assert.assertEquals("select e from test$AdGroup e where e.name like :a", query);
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
        .build();
    Assert.assertEquals(
        "select e from test$AdGroup e " +
            "where (e.name = :a or e.status = :b) " +
            "and e.id <> :c",
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
        .build();
    Assert.assertEquals(
        "select e from test$AdGroup e " +
            "where e.id <> :a " +
            "and e.status <> :b " +
            "and e.campaign.status <> :c " +
            "and e.campaign.advertiser.status <> :d " +
            "and e.campaign.name like :e " +
            "or e.name is null " +
            "or e.campaign.id is not null " +
            "and (" +
              "e.campaign.name not like :f " +
              "or e.campaign.advertiser.name like :g escape :h " +
              "or (not (e.status = :i and e.name like :j)) " +
              "and e.id between :k and :l " +
              "and (e.status in :m or e.status not in :n)" +
            ") " +
            "or (e.id >= :o and e.id <= :p)",
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
}
