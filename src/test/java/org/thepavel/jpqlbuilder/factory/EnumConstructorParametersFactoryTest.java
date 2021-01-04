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

import org.junit.Before;
import org.junit.Test;
import org.thepavel.jpqlbuilder.model.Status;

import java.lang.reflect.Constructor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class EnumConstructorParametersFactoryTest {
  @Test
  public void enumNameAndOrdinal() {
    Object[] params = factory.getParameters(getConstructor(Status.class));

    assertEquals(2, params.length);
    assertTrue(params[0] instanceof String); // enum name
    assertTrue(params[1] instanceof Integer); // ordinal
  }

  @Test
  public void extraConstructorParamsAreNull() {
    Object[] params = factory.getParameters(getConstructor(TestEnum.class));

    assertEquals(4, params.length);
    assertTrue(params[0] instanceof String); // enum name
    assertTrue(params[1] instanceof Integer); // ordinal
    assertNull(params[2]);
    assertNull(params[3]);
  }

  private ConstructorParametersFactory factory;

  @Before
  public void setup() {
    factory = new EnumConstructorParametersFactory();
  }

  private static Constructor<?> getConstructor(Class<?> enumClass) {
    return enumClass.getDeclaredConstructors()[0];
  }

  @SuppressWarnings("unused")
  private enum TestEnum {
    ONE("A", 1L),
    TWO("B", 2L);

    TestEnum(String a, Long b) {
    }
  }
}
