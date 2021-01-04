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

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseUpdateChain<B extends BaseUpdateChain<B>> {
  private final Map<Object, Object> updates = new LinkedHashMap<>();

  public Map<Object, Object> getUpdates() {
    return updates;
  }

  public <T> UpdateBuilder<T, B> set(T field) {
    return new UpdateBuilder<>(thisChain(), field);
  }

  @SuppressWarnings("unchecked")
  private B thisChain() {
    return (B) this;
  }

  <T> void addUpdate(T field, T value) {
    updates.put(field, value);
  }
}
