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

package org.thepavel.jpqlbuilder.samples;

import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.thepavel.jpqlbuilder.SelectBuilder;
import org.thepavel.jpqlbuilder.factory.DefaultMapInstanceFactory;
import org.thepavel.jpqlbuilder.factory.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.thepavel.jpqlbuilder.JpqlBuilder;
import org.thepavel.jpqlbuilder.factory.DefaultCollectionInstanceFactory;
import org.thepavel.jpqlbuilder.factory.DefaultInstanceFactory;
import org.thepavel.jpqlbuilder.model.Company;
import org.thepavel.jpqlbuilder.model.Department;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CustomFactoryTest {
  @Test
  public void defaultFactories() {
    SelectBuilder builder = JpqlBuilder.selectBuilder();
    Department department = builder.from(Department.class);

    assertTrue(AopUtils.isAopProxy(department));
    assertNotNull(department.getName());
    assertTrue(department.getEmployees() instanceof ArrayList);
    assertTrue(AopUtils.isAopProxy(department.getCompany()));
  }

  @Test
  public void customInstanceFactory() {
    DefaultInstanceFactory instanceFactory = new DefaultInstanceFactory();
    instanceFactory.add(String.class, () -> "test");

    SelectBuilder builder = JpqlBuilder.with(instanceFactory).selectBuilder();
    Department department = builder.from(Department.class);

    assertTrue(AopUtils.isAopProxy(department));
    assertEquals("test", department.getName());
    assertTrue(department.getEmployees() instanceof ArrayList);
    assertTrue(AopUtils.isAopProxy(department.getCompany()));
  }

  @Test
  public void customCollectionInstanceFactory() {
    DefaultCollectionInstanceFactory collectionInstanceFactory = new DefaultCollectionInstanceFactory();
    collectionInstanceFactory.add(List.class, Vector::new);

    SelectBuilder builder = JpqlBuilder.with(collectionInstanceFactory).selectBuilder();
    Department department = builder.from(Department.class);

    assertTrue(AopUtils.isAopProxy(department));
    assertNotNull(department.getName());
    assertTrue(department.getEmployees() instanceof Vector);
    assertTrue(AopUtils.isAopProxy(department.getCompany()));
  }

  @Test
  public void customMapInstanceFactory() {
    DefaultMapInstanceFactory mapInstanceFactory = new DefaultMapInstanceFactory();
    mapInstanceFactory.add(Map.class, TreeMap::new);

    SelectBuilder builder = JpqlBuilder.with(mapInstanceFactory).selectBuilder();
    Company company = builder.from(Company.class);

    assertTrue(AopUtils.isAopProxy(company));
    assertNotNull(company.getName());
    assertTrue(company.getHeads() instanceof TreeMap);
    assertTrue(company.getDepartments() instanceof ArrayList);
  }

  @Test
  public void customProxyFactory() {
    SelectBuilder builder = JpqlBuilder.with(new TestProxyFactory()).selectBuilder();
    Department department = builder.from(Department.class);

    assertFalse(AopUtils.isAopProxy(department));
    assertNull(department.getName());
    assertNull(department.getEmployees());
    assertNull(department.getCompany());
  }

  private static class TestProxyFactory implements ProxyFactory {
    @Override
    public <T> T createProxy(Class<T> type, Advice advice) {
      try {
        return type.newInstance();
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e.getMessage(), e);
      }
    }

    @Override
    public <T> T createProxy(T target, Advice advice) {
      return target;
    }
  }
}
