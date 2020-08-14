package org.test.query;

import org.test.JpqlStringBuilder;
import org.test.operators.Operator;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery extends Operator {
  private final SelectClause select;
  private final List<JoinClause> joins = new ArrayList<>();
  private final WhereClause where = new WhereClause();
  private final OrderByClause orderBy = new OrderByClause();

  public SelectQuery(String alias, Class<?> entityClass) {
    select = new SelectClause(alias, entityClass);
  }

  public void addJoin(JoinClause join) {
    joins.add(join);
  }

  public void setWhere(Operator operator) {
    where.setOperator(operator);
  }

  public void addOrderBy(Object operand) {
    orderBy.addItem(operand);
  }

  public void setOrderAsc() {
    orderBy.setAsc();
  }

  public void setOrderDesc() {
    orderBy.setDesc();
  }

  public void setOrderNullsFirst() {
    orderBy.setNullsFirst();
  }

  public void setOrderNullsLast() {
    orderBy.setNullsLast();
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    select.writeTo(stringBuilder);
    joins.forEach(join -> join.writeTo(stringBuilder));
    where.writeTo(stringBuilder);
    orderBy.writeTo(stringBuilder);
  }
}
