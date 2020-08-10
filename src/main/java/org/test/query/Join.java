package org.test.query;

import org.test.JpqlStringBuilder;
import org.test.operators.Operator;

import java.util.ArrayList;
import java.util.List;

public class Join extends Operator {
  private final List<String> aliases = new ArrayList<>();
  private final List<Object> paths = new ArrayList<>();
  private final List<JoinType> types = new ArrayList<>();

  public void add(String alias, Object path, JoinType type) {
    aliases.add(alias);
    paths.add(path);
    types.add(type);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    for (int i = 0; i < aliases.size(); i++) {
      stringBuilder.appendString(types.get(i).getClause());
      stringBuilder.appendValue(paths.get(i));
      appendAlias(stringBuilder, i);
    }
  }

  private void appendAlias(JpqlStringBuilder<?> stringBuilder, int i) {
    if (types.get(i).hasAlias()) {
      stringBuilder.appendString(" ");
      stringBuilder.appendString(aliases.get(i));
    }
  }
}
