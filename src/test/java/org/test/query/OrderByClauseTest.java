package org.test.query;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.test.DummyJpqlStringWriter.asString;

public class OrderByClauseTest {
  @Test
  public void empty() {
    assertEquals("", asString(new OrderByClause()));
  }

  @Test
  public void oneItem() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");

    assertEquals(" order by A", asString(orderBy));
  }

  @Test
  public void multipleItems() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.addItem("B");
    orderBy.addItem("C");

    assertEquals(" order by A, B, C", asString(orderBy));
  }

  @Test
  public void asc() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setAsc();

    assertEquals(" order by A asc", asString(orderBy));
  }

  @Test
  public void desc() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();

    assertEquals(" order by A desc", asString(orderBy));
  }

  @Test
  public void multipleCallsToAscAndDesc() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setAsc();
    orderBy.setDesc();

    assertEquals(" order by A desc", asString(orderBy));
  }

  @Test
  public void nullsFirst() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setNullsFirst();

    assertEquals(" order by A nulls first", asString(orderBy));
  }

  @Test
  public void nullsLast() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setNullsLast();

    assertEquals(" order by A nulls last", asString(orderBy));
  }

  @Test
  public void multipleCallsToNullsFirstAndNullsLast() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setNullsFirst();
    orderBy.setNullsLast();

    assertEquals(" order by A nulls last", asString(orderBy));
  }

  @Test
  public void ascNullsFirst() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setAsc();
    orderBy.setNullsFirst();

    assertEquals(" order by A asc nulls first", asString(orderBy));
  }

  @Test
  public void ascNullsLast() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setAsc();
    orderBy.setNullsLast();

    assertEquals(" order by A asc nulls last", asString(orderBy));
  }

  @Test
  public void descNullsFirst() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();
    orderBy.setNullsFirst();

    assertEquals(" order by A desc nulls first", asString(orderBy));
  }

  @Test
  public void descNullsLast() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();
    orderBy.setNullsLast();

    assertEquals(" order by A desc nulls last", asString(orderBy));
  }

  @Test
  public void multipleItemsWithOrdering() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();
    orderBy.addItem("B");
    orderBy.setAsc();
    orderBy.addItem("C");

    assertEquals(" order by A desc, B asc, C", asString(orderBy));
  }

  @Test
  public void multipleItemsWithNullsOrdering() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setNullsLast();
    orderBy.addItem("B");
    orderBy.setNullsFirst();
    orderBy.addItem("C");

    assertEquals(" order by A nulls last, B nulls first, C", asString(orderBy));
  }

  @Test
  public void multipleItemsWithOrderingAndNullsOrdering() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();
    orderBy.setNullsFirst();
    orderBy.addItem("B");
    orderBy.setAsc();
    orderBy.setNullsLast();
    orderBy.addItem("C");

    assertEquals(" order by A desc nulls first, B asc nulls last, C", asString(orderBy));
  }
}
