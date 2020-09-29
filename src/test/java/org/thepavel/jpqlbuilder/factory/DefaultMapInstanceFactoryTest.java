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

import java.util.Map;
import java.util.SortedMap;

import static org.junit.Assert.assertNotSame;

public class DefaultMapInstanceFactoryTest {
  @Test
  public void newMap() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(Map.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void unsupportedMap() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(SortedMap.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void notMap() throws ReflectiveOperationException {
    factoryCreatesUniqueInstance(String.class);
  }

  private void factoryCreatesUniqueInstance(Class<?> type) throws ReflectiveOperationException {
    MapInstanceFactory factory = new DefaultMapInstanceFactory();
    assertNotSame(factory.newInstance(type), factory.newInstance(type));
  }
}
