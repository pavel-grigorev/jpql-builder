package org.test.query;

public enum JoinType {
  INNER(" join ", true),
  LEFT(" left join ", true),
  FETCH(" join fetch ", false),
  FETCH_WITH_ALIAS(" join fetch ", true);

  private final String clause;
  private final boolean hasAlias;

  JoinType(String clause, boolean hasAlias) {
    this.clause = clause;
    this.hasAlias = hasAlias;
  }

  public String getClause() {
    return clause;
  }

  public boolean hasAlias() {
    return hasAlias;
  }
}
