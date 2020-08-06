package org.test.query;

import org.test.JpqlStringBuilder;
import org.test.operators.Operator;

import java.util.ArrayList;
import java.util.List;

public class OrderBy extends Operator {
  private final List<Item> items = new ArrayList<>();

  public OrderBy(Object operand) {
    addItem(operand);
  }

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
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    stringBuilder.appendString(" order by ");

    for (int i = 0; i < items.size(); i++) {
      if (i > 0) {
        stringBuilder.appendString(", ");
      }
      writeItem(items.get(i), stringBuilder);
    }
  }

  private void writeItem(Item item, JpqlStringBuilder<?> stringBuilder) {
    writeOperand(item.operand, stringBuilder);

    if (item.ordering != null) {
      stringBuilder.appendString(item.ordering);
    }

    if (item.nullsOrdering != null) {
      stringBuilder.appendString(item.nullsOrdering);
    }
  }

  private static class Item {
    private Object operand;
    private String ordering;
    private String nullsOrdering;
  }
}
