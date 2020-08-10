package org.test.query;

public enum JoinType {
  INNER(" join "),
  LEFT(" left join "),
  FETCH(" join fetch ");

  private final String clause;

  JoinType(String clause) {
    this.clause = clause;
  }

  public String getClause() {
    return clause;
  }
}
