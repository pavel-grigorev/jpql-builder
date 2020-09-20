package org.pavel.jpqlbuilder.query;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.pavel.jpqlbuilder.operators.Operator;

import java.util.ArrayList;
import java.util.List;

public class OrderByClause implements Operator {
  private final List<Item> items = new ArrayList<>();

  public void addItem(Object operand) {
    Item item = new Item();
    item.operand = operand;
    items.add(item);
  }

  public void setAsc() {
    getLastItem().ordering = " asc";
  }

  public void setDesc() {
    getLastItem().ordering = " desc";
  }

  public void setNullsFirst() {
    getLastItem().nullsOrdering = " nulls first";
  }

  public void setNullsLast() {
    getLastItem().nullsOrdering = " nulls last";
  }

  private Item getLastItem() {
    return items.get(items.size() - 1);
  }

  @Override
  public void writeTo(JpqlStringWriter stringWriter) {
    if (items.isEmpty()) {
      return;
    }
    stringWriter.appendString(" order by ");

    for (int i = 0; i < items.size(); i++) {
      if (i > 0) {
        stringWriter.appendString(", ");
      }
      writeItem(items.get(i), stringWriter);
    }
  }

  private void writeItem(Item item, JpqlStringWriter stringWriter) {
    writeOperand(item.operand, stringWriter);

    if (item.ordering != null) {
      stringWriter.appendString(item.ordering);
    }

    if (item.nullsOrdering != null) {
      stringWriter.appendString(item.nullsOrdering);
    }
  }

  private static class Item {
    private Object operand;
    private String ordering;
    private String nullsOrdering;
  }
}
