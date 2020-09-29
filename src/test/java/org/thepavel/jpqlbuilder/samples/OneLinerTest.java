package org.thepavel.jpqlbuilder.samples;

import org.junit.Test;
import org.thepavel.jpqlbuilder.JpqlQuery;
import org.thepavel.jpqlbuilder.model.Company;
import org.thepavel.jpqlbuilder.model.Status;
import org.thepavel.jpqlbuilder.JpqlBuilder;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.operators.builders.OperatorBuilder.$;

public class OneLinerTest {
  @Test
  public void oneLinerOrderBy() {
    JpqlQuery query = JpqlBuilder.select(Company.class).orderBy(Company::getName).desc().orderBy(Company::getId);

    String expected = "select a from test_Company a order by a.name desc, a.id";

    assertEquals(expected, query.getQueryString());
    assertEquals(new HashMap<String, Object>(), query.getParameters());
  }

  @Test
  public void oneLinerWhere() {
    JpqlQuery query = JpqlBuilder.select(Company.class).where(c -> $(c.getStatus()).isNot(Status.DELETED));

    String expected = "select a from test_Company a where a.status <> :a";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
        }},
        query.getParameters()
    );
  }

  @Test
  public void oneLinerWhereAndOrderBy() {
    JpqlQuery query = JpqlBuilder
        .select(Company.class)
        .where(c -> $(c.getStatus()).isNot(Status.DELETED).and(c.getName()).like("%test%"))
        .orderBy(Company::getName).desc();

    String expected = "select a from test_Company a where a.status <> :a and a.name like :b order by a.name desc";

    assertEquals(expected, query.getQueryString());
    assertEquals(
        new HashMap<String, Object>() {{
          put("a", Status.DELETED);
          put("b", "%test%");
        }},
        query.getParameters()
    );
  }
}
