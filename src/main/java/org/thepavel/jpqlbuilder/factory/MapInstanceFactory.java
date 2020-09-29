package org.thepavel.jpqlbuilder.factory;

import java.util.Map;

public interface MapInstanceFactory {
  <T extends Map<K, V>, K, V> T newInstance(Class<?> type) throws ReflectiveOperationException;
}
