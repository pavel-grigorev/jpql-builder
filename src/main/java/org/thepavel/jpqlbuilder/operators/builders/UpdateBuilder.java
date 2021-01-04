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

import org.thepavel.jpqlbuilder.functions.JpqlFunction;

public class UpdateBuilder<T, B extends BaseUpdateChain<B>> {
  final B chain;
  final T field;

  public UpdateBuilder(B chain, T field) {
    this.chain = chain;
    this.field = field;
  }

  public static <T> UpdateBuilder<T, UpdateChain> set(T field) {
    return new UpdateBuilder<>(new UpdateChain(), field);
  }

  public B to(T value) {
    chain.addUpdate(field, value);
    return chain;
  }

  public B to(JpqlFunction<T> value) {
    chain.addUpdate(field, value);
    return chain;
  }
}
