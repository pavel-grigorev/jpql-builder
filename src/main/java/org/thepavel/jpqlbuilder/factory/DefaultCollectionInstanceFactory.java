/*
 * Copyright (c) 2020-2021 Pavel Grigorev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thepavel.jpqlbuilder.factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class DefaultCollectionInstanceFactory implements CollectionInstanceFactory {
  private final Map<Class<?>, Supplier<?>> instanceCreators = new HashMap<>();

  public DefaultCollectionInstanceFactory() {
    instanceCreators.put(List.class, ArrayList::new);
    instanceCreators.put(Set.class, HashSet::new);
  }

  public void add(Class<?> type, Supplier<?> instanceCreator) {
    checkType(type);
    instanceCreators.put(type, instanceCreator);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Collection<E>, E> T newInstance(Class<?> type) throws ReflectiveOperationException {
    checkType(type);
    Supplier<?> creator = instanceCreators.get(type);
    if (creator == null) {
      throw new IllegalArgumentException("Collection type " + type + " is unsupported");
    }
    return (T) creator.get();
  }

  private static void checkType(Class<?> type) {
    if (!Collection.class.isAssignableFrom(type)) {
      throw new IllegalArgumentException("Class " + type + " is not a collection");
    }
  }
}
