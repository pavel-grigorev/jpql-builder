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

package org.thepavel.jpqlbuilder.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathResolverList {
  private final List<PathResolver<?>> list = new ArrayList<>();

  public void add(PathResolver<?> pathResolver) {
    list.add(pathResolver);
  }

  public String getPropertyPath(Object value) {
    return list
        .stream()
        .map(r -> r.getPropertyPath(value))
        .filter(Objects::nonNull)
        .findFirst()
        .orElse(null);
  }
}
