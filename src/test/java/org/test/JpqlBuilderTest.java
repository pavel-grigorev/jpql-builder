package org.test;

import org.junit.Test;
import org.test.entities.AdGroup;
import org.test.entities.Status;

import static org.test.operators.builders.OperatorBuilder.$;
import static org.test.operators.builders.OperatorBuilder.not;
import static org.test.operators.builders.StringOperatorBuilder.$;

public class JpqlBuilderTest {
  @Test
  public void test() {
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
            $(adGroup.getCampaign().getName()).like("A")
                .or(adGroup.getCampaign().getAdvertiser().getName()).like("A")
                .or(not(
                    $(adGroup.getStatus()).is(Status.ACTIVE)
                        .and(adGroup.getName()).like("B")
                ))
                .and(adGroup.getStatus()).is(Status.SUSPENDED)
        )
        .build();
    System.out.println(query);
    System.out.println(select.getParameters());
  }
}
