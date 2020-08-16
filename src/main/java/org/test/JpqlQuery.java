package org.test;

import java.util.Map;

public interface JpqlQuery {
  String getQueryString();
  Map<String, Object> getParameters();
}
