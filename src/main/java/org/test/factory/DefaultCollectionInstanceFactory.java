package org.test.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultCollectionInstanceFactory implements CollectionInstanceFactory {
  private final Map<Class<?>, InstanceCreator<?>> instanceCreators = new HashMap<>();

  public DefaultCollectionInstanceFactory() {
    instanceCreators.put(List.class, ArrayList::new);
    instanceCreators.put(Set.class, HashSet::new);
  }

  public void add(Class<?> type, InstanceCreator<?> instanceCreator) {
    checkType(type);
    instanceCreators.put(type, instanceCreator);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Collection<E>, E> T newInstance(Class<?> type) throws ReflectiveOperationException {
    checkType(type);
    InstanceCreator<?> creator = instanceCreators.get(type);
    if (creator == null) {
      throw new IllegalArgumentException("Collection type " + type + " is unsupported");
    }
    return (T) creator.newInstance();
  }

  private static void checkType(Class<?> type) {
    if (!Collection.class.isAssignableFrom(type)) {
      throw new IllegalArgumentException("Class " + type + " is not a collection class");
    }
  }
}
