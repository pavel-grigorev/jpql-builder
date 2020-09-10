package org.test.factory;

import java.util.Map;

public interface MapInstanceFactory {
  <T extends Map<K, V>, K, V> T newInstance(Class<?> type) throws ReflectiveOperationException;
}
