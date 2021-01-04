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

import org.junit.Test;
import org.thepavel.jpqlbuilder.model.Status;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EnumFactoryTest {
  @Test
  public void returnsUniqueInstance() {
    Object instance = new EnumFactory().newInstance(Status.class);

    assertNotNull(instance);
    assertTrue(instance instanceof Status);
    assertTrue(Arrays.stream(Status.values()).noneMatch(v -> v == instance));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nonEnumClass() {
    new EnumFactory().newInstance(String.class);
  }
}
