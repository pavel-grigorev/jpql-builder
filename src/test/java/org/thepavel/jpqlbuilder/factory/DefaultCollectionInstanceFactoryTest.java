/*
 * Copyright (c) 2020 Pavel Grigorev.
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

import org.junit.Test;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import static org.junit.Assert.assertNotSame;

public class DefaultCollectionInstanceFactoryTest {
  @Test
  public void newList() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(List.class);
  }

  @Test
  public void newSet() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Set.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void unsupportedCollection() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Queue.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void notCollection() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(String.class);
  }

  private void factoryCreatesUniqueInstance(Class<?> type) throws ReflectiveOperationException {
    CollectionInstanceFactory factory = new DefaultCollectionInstanceFactory();
    assertNotSame(factory.newInstance(type), factory.newInstance(type));
  }
}
