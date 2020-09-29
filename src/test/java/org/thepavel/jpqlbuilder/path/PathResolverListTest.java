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

package org.thepavel.jpqlbuilder.path;

import org.junit.Before;
import org.junit.Test;
import org.thepavel.jpqlbuilder.JpqlBuilderContext;
import org.thepavel.jpqlbuilder.model.Company;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PathResolverListTest {
  @Test
  public void propertyPath() {
    Company company = pathResolver.getPathSpecifier();

    assertEquals("a.name", list.getPropertyPath(company.getName()));
  }

  @Test
  public void unrecognizedPropertyPath() {
    assertNull(list.getPropertyPath(new Object()));
  }

  private PathResolver<Company> pathResolver;
  private PathResolverList list;

  @Before
  public void setup() {
    pathResolver = new PathResolver<>(Company.class, "a", JpqlBuilderContext.defaultContext());

    list = new PathResolverList();
    list.add(pathResolver);
  }
}
