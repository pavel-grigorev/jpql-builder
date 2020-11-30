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

package org.thepavel.jpqlbuilder;

import org.junit.Test;
import org.thepavel.jpqlbuilder.model.Company;

public class DeleteBuilderTest {
  @Test(expected = IllegalStateException.class)
  public void allowedToCallFromOnce() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();
    builder.from(Company.class);
    builder.from(Company.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void fromRequiresEntityClass() {
    JpqlBuilder.deleteBuilder().from(DummyObject.class);
  }

  @Test(expected = IllegalStateException.class)
  public void allowedToCallDeleteOnce() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();
    Company c = builder.from(Company.class);

    builder.delete(c);
    builder.delete(c);
  }

  @Test(expected = IllegalStateException.class)
  public void allowedToCallOneLinerOnce() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();

    builder.delete(Company.class);
    builder.delete(Company.class);
  }

  @Test(expected = IllegalStateException.class)
  public void mixedCallsNotAllowed() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();
    Company c = builder.from(Company.class);

    builder.delete(c);
    builder.delete(Company.class);
  }

  @Test(expected = IllegalStateException.class)
  public void mixedCallsNotAllowed2() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();
    Company c = builder.from(Company.class);

    builder.delete(Company.class);
    builder.delete(c);
  }

  @Test(expected = IllegalArgumentException.class)
  public void deleteExpectsObjectReturnedByFrom() {
    DeleteBuilder builder = JpqlBuilder.deleteBuilder();
    builder.delete(new DummyObject("", 0));
  }
}
