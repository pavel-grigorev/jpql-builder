package org.pavel.jpqlbuilder.query;

import org.junit.Assert;
import org.junit.Test;
import org.pavel.jpqlbuilder.DummyJpqlStringWriter;

import static org.junit.Assert.assertEquals;

public class OrderByClauseTest {
  @Test
  public void empty() {
    Assert.assertEquals("", DummyJpqlStringWriter.asString(new OrderByClause()));
  }

  @Test
  public void oneItem() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");

    Assert.assertEquals(" order by A", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void multipleItems() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.addItem("B");
    orderBy.addItem("C");

    Assert.assertEquals(" order by A, B, C", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void asc() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setAsc();

    Assert.assertEquals(" order by A asc", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void desc() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();

    Assert.assertEquals(" order by A desc", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void multipleCallsToAscAndDesc() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setAsc();
    orderBy.setDesc();

    Assert.assertEquals(" order by A desc", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void nullsFirst() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setNullsFirst();

    Assert.assertEquals(" order by A nulls first", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void nullsLast() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setNullsLast();

    Assert.assertEquals(" order by A nulls last", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void multipleCallsToNullsFirstAndNullsLast() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setNullsFirst();
    orderBy.setNullsLast();

    Assert.assertEquals(" order by A nulls last", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void ascNullsFirst() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setAsc();
    orderBy.setNullsFirst();

    Assert.assertEquals(" order by A asc nulls first", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void ascNullsLast() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setAsc();
    orderBy.setNullsLast();

    Assert.assertEquals(" order by A asc nulls last", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void descNullsFirst() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();
    orderBy.setNullsFirst();

    Assert.assertEquals(" order by A desc nulls first", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void descNullsLast() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();
    orderBy.setNullsLast();

    Assert.assertEquals(" order by A desc nulls last", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void multipleItemsWithOrdering() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setDesc();
    orderBy.addItem("B");
    orderBy.setAsc();
    orderBy.addItem("C");

    Assert.assertEquals(" order by A desc, B asc, C", DummyJpqlStringWriter.asString(orderBy));
  }

  @Test
  public void multipleItemsWithNullsOrdering() {
    OrderByClause orderBy = new OrderByClause();
    orderBy.addItem("A");
    orderBy.setNullsLast();
    orderBy.addItem("B");
    orderBy.setNullsFirst();
    orderBy.addItem("C");

    Assert.assertEquals(" order by A nulls last, B nulls first, C", DummyJpqlStringWriter.asString(orderBy));
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

    Assert.assertEquals(" order by A desc nulls first, B asc nulls last, C", DummyJpqlStringWriter.asString(orderBy));
  }
}
