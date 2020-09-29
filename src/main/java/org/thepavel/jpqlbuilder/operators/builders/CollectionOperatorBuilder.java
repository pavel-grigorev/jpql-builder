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

import org.thepavel.jpqlbuilder.operators.IsEmpty;
import org.thepavel.jpqlbuilder.operators.IsNotEmpty;

import java.util.Collection;

public class CollectionOperatorBuilder<B extends BaseExpressionChain<B>> {
  final B chain;
  final Collection<?> operand;

  public CollectionOperatorBuilder(B chain, Collection<?> operand) {
    this.chain = chain;
    this.operand = operand;
  }

  public B isEmpty() {
    return chain.join(new IsEmpty(operand));
  }

  public B isNotEmpty() {
    return chain.join(new IsNotEmpty(operand));
  }
}
