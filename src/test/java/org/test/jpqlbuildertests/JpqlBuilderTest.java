package org.test.jpqlbuildertests;

import org.junit.Assert;
import org.junit.Test;
import org.test.JpqlBuilder;
import org.test.entities.AdGroup;
import org.test.entities.Status;

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
                .and(adGroup.getStatus()).is(Status.SUSPENDED)
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
              "and e.status = :k" +
            ")",
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
          put("k", Status.SUSPENDED);
        }},
        select.getParameters()
    );
  }
}
