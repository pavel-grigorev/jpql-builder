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

package org.thepavel.jpqlbuilder.operators.builders;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class UpdateChainTest {
  @Test
  public void set() {
    UpdateChain chain = new UpdateChain();
    chain.set("A").to("B");

    assertEquals(
        new HashMap<Object, Object>() {{
          put("A", "B");
        }},
        chain.getUpdates()
    );
  }

  @Test
  public void addUpdate() {
    UpdateChain chain = new UpdateChain();
    chain.addUpdate("X", "Y");

    assertEquals(
        new HashMap<Object, Object>() {{
          put("X", "Y");
        }},
        chain.getUpdates()
    );
  }
}
