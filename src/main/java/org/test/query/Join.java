package org.test.query;

import org.test.JpqlStringBuilder;
import org.test.operators.Operator;

import java.util.ArrayList;
import java.util.List;

public class Join extends Operator {
  private final List<String> aliases = new ArrayList<>();
  private final List<Object> paths = new ArrayList<>();

  public void add(String alias, Object path) {
    aliases.add(alias);
    paths.add(path);
  }

  @Override
  public void writeTo(JpqlStringBuilder<?> stringBuilder) {
    for (int i = 0; i < aliases.size(); i++) {
      stringBuilder.appendString(" join ");
      stringBuilder.appendValue(paths.get(i));
      stringBuilder.appendString(" ");
      stringBuilder.appendString(aliases.get(i));
    }
  }
}
