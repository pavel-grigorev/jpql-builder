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
import org.thepavel.jpqlbuilder.functions.JpqlFunction;
import org.thepavel.jpqlbuilder.utils.Pair;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.DummyFunction.dummy;
import static org.thepavel.jpqlbuilder.operators.builders.UpdateBuilder.set;

public class UpdateBuilderTest {
  @Test
  public void toValue() {
    UpdateChain chain = new UpdateChain();
    UpdateBuilder<String, UpdateChain> builder = new UpdateBuilder<>(chain, "A");
    List<Pair<Object, Object>> updates = builder.to("B").getUpdates();

    assertEquals(
        singletonList(Pair.of("A", "B")),
        updates
    );
  }

  @Test
  public void toFunction() {
    UpdateChain chain = new UpdateChain();
    UpdateBuilder<String, UpdateChain> builder = new UpdateBuilder<>(chain, "A");

    JpqlFunction<String> function = dummy("B");
    List<Pair<Object, Object>> updates = builder.to(function).getUpdates();

    assertEquals(
        singletonList(Pair.of("A", function)),
        updates
    );
  }

  @Test
  public void staticConstructor() {
    assertEquals(
        singletonList(Pair.of("A", "B")),
        set("A").to("B").getUpdates()
    );
  }
}
