package org.pavel.jpqlbuilder.query;

import org.pavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.pavel.jpqlbuilder.operators.Operator;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery implements Operator {
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
  public void writeTo(JpqlStringWriter stringWriter) {
    select.writeTo(stringWriter);
    joins.forEach(join -> join.writeTo(stringWriter));
    where.writeTo(stringWriter);
    orderBy.writeTo(stringWriter);
  }
}
