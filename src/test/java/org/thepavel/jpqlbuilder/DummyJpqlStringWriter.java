package org.thepavel.jpqlbuilder;

import org.thepavel.jpqlbuilder.querystring.JpqlStringWriter;
import org.springframework.aop.support.AopUtils;
import org.thepavel.jpqlbuilder.operators.Operator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DummyJpqlStringWriter implements JpqlStringWriter {
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  private final StringBuilder builder = new StringBuilder();

  @Override
  public void appendString(String string) {
    builder.append(string);
  }

  @Override
  public void appendValue(Object value) {
    if (value instanceof Class) {
      builder.append(((Class<?>) value).getSimpleName());
      return;
    }
    if (value instanceof Date) {
      builder.append(dateFormat.format((Date) value));
      return;
    }
    if (AopUtils.isAopProxy(value)) {
      builder.append(AopUtils.getTargetClass(value).getSimpleName());
      return;
    }
    builder.append(value);
  }

  @Override
  public String toString() {
    return builder.toString();
  }

  public static String asString(Operator operator) {
    JpqlStringWriter stringWriter = new DummyJpqlStringWriter();
    operator.writeTo(stringWriter);
    return stringWriter.toString();
  }
}
