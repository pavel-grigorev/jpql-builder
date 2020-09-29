package org.thepavel.jpqlbuilder.factory;

import java.util.HashMap;
import java.util.Map;

public class DefaultMapInstanceFactory implements MapInstanceFactory {
  private final Map<Class<?>, InstanceCreator<?>> instanceCreators = new HashMap<>();

  public DefaultMapInstanceFactory() {
    instanceCreators.put(Map.class, HashMap::new);
  }

  public void add(Class<?> type, InstanceCreator<?> instanceCreator) {
    checkType(type);
    instanceCreators.put(type, instanceCreator);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Map<K, V>, K, V> T newInstance(Class<?> type) throws ReflectiveOperationException {
    checkType(type);
    InstanceCreator<?> creator = instanceCreators.get(type);
    if (creator == null) {
      throw new IllegalArgumentException("Map type " + type + " is unsupported");
    }
    return (T) creator.newInstance();
  }

  private static void checkType(Class<?> type) {
    if (!Map.class.isAssignableFrom(type)) {
      throw new IllegalArgumentException("Class " + type + " is not a map class");
    }
  }
}
