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

package org.thepavel.jpqlbuilder.utils;

import org.junit.Test;
import org.thepavel.jpqlbuilder.model.Status;

import static org.junit.Assert.assertNotSame;

public class ObjectHelperTest {
  @Test
  public void createsUniqueInstanceOfEnum() {
    assertNotSame(ObjectHelper.newInstance(Status.class), ObjectHelper.newInstance(Status.class));
  }

  @Test
  public void enumWithCustomArgument() {
    assertNotSame(ObjectHelper.newInstance(Currency.class), ObjectHelper.newInstance(Currency.class));
  }

  private enum Currency {
    USD("USD"),
    EUR("EUR");

    final String code;

    Currency(String code) {
      this.code = code;
    }
  }
}