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

import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.thepavel.jpqlbuilder.model.Company;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class JpqlBuilderContextTest {
  @Test
  public void newInstance() throws ReflectiveOperationException {
    assertNotSame(context.newInstance(String.class), context.newInstance(String.class));
  }

  @Test
  public void newCollectionInstance() throws ReflectiveOperationException {
    assertNotSame(context.newCollectionInstance(List.class), context.newCollectionInstance(List.class));
  }

  @Test
  public void newMapInstance() throws ReflectiveOperationException {
    assertNotSame(context.newMapInstance(Map.class), context.newMapInstance(Map.class));
  }

  @Test
  public void createProxyForClass() {
    Company proxy = context.createProxy(Company.class, new DummyAdvice());

    assertNotNull(proxy);
    assertTrue(AopUtils.isAopProxy(proxy));
  }

  @Test
  public void createProxyForObject() {
    Company company = new Company();
    Company proxy = context.createProxy(company, new DummyAdvice());

    assertNotNull(proxy);
    assertTrue(AopUtils.isAopProxy(proxy));
    assertNotSame(company, proxy);
  }

  private JpqlBuilderContext context;

  @Before
  public void setup() {
    context = JpqlBuilderContext.defaultContext();
  }
}
