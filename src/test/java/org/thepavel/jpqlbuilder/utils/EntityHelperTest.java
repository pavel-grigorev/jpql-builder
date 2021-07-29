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

package org.thepavel.jpqlbuilder.utils;

import org.junit.Test;
import org.thepavel.jpqlbuilder.model.Company;
import org.thepavel.jpqlbuilder.proxy.EntityProxyFactory;

import javax.persistence.Entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EntityHelperTest {
  @Test
  public void isEntity() {
    assertTrue(EntityHelper.isEntity(Company.class));
    assertTrue(EntityHelper.isEntity(TestEntity.class));
    assertFalse(EntityHelper.isEntity(String.class));
  }

  @Test
  public void getEntityName() {
    assertEquals("test_Company", EntityHelper.getEntityName(Company.class));
    assertEquals("TestEntity", EntityHelper.getEntityName(TestEntity.class));
  }

  @Test(expected = NullPointerException.class)
  public void getEntityNameOfNonEntity() {
    EntityHelper.getEntityName(String.class);
  }

  @Test
  public void isProxiedEntity() {
    Company company = EntityProxyFactory.createProxy(Company.class, null);
    NonEntity nonEntity = EntityProxyFactory.createProxy(NonEntity.class, null);

    assertTrue(EntityHelper.isProxiedEntity(company));
    assertFalse(EntityHelper.isProxiedEntity(nonEntity));
    assertFalse(EntityHelper.isProxiedEntity(new Company()));
  }

  @Entity
  private static class TestEntity {
  }

  public static class NonEntity {
  }
}
