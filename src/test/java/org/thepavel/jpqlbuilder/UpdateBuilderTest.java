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

package org.thepavel.jpqlbuilder;

import org.junit.Test;
import org.thepavel.jpqlbuilder.model.Company;

public class UpdateBuilderTest {
  @Test(expected = IllegalStateException.class)
  public void allowedToCallEntityOnce() {
    UpdateBuilder builder = JpqlBuilder.updateBuilder();
    builder.entity(Company.class);
    builder.entity(Company.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void entityRequiresEntityClass() {
    JpqlBuilder.updateBuilder().entity(DummyObject.class);
  }

  @Test(expected = IllegalStateException.class)
  public void allowedToCallUpdateOnce() {
    UpdateBuilder builder = JpqlBuilder.updateBuilder();
    Company c = builder.entity(Company.class);

    builder.update(c);
    builder.update(c);
  }

  @Test(expected = IllegalStateException.class)
  public void allowedToCallOneLinerOnce() {
    UpdateBuilder builder = JpqlBuilder.updateBuilder();

    builder.update(Company.class);
    builder.update(Company.class);
  }

  @Test(expected = IllegalStateException.class)
  public void mixedCallsNotAllowed() {
    UpdateBuilder builder = JpqlBuilder.updateBuilder();
    Company c = builder.entity(Company.class);

    builder.update(c);
    builder.update(Company.class);
  }

  @Test(expected = IllegalStateException.class)
  public void mixedCallsNotAllowed2() {
    UpdateBuilder builder = JpqlBuilder.updateBuilder();
    Company c = builder.entity(Company.class);

    builder.update(Company.class);
    builder.update(c);
  }

  @Test(expected = IllegalArgumentException.class)
  public void updateExpectsObjectReturnedByEntity() {
    UpdateBuilder builder = JpqlBuilder.updateBuilder();
    builder.update(new DummyObject("", 0));
  }
}
