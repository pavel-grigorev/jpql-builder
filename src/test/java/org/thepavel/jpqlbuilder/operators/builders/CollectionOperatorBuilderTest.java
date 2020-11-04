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

package org.thepavel.jpqlbuilder.operators.builders;

import org.junit.Test;
import org.thepavel.jpqlbuilder.DummyJpqlStringWriter;
import org.thepavel.jpqlbuilder.operators.IsEmpty;
import org.thepavel.jpqlbuilder.operators.IsNotEmpty;
import org.thepavel.jpqlbuilder.operators.Operator;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectionOperatorBuilderTest {
  @Test
  public void isEmpty() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new CollectionOperatorBuilder<>(chain, new ArrayList<>()).isEmpty().getOperator();

    assertTrue(operator instanceof IsEmpty);
    assertEquals("[] is empty", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void isNotEmpty() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new CollectionOperatorBuilder<>(chain, new ArrayList<>()).isNotEmpty().getOperator();

    assertTrue(operator instanceof IsNotEmpty);
    assertEquals("[] is not empty", DummyJpqlStringWriter.asString(operator));
  }
}
