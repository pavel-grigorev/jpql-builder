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
import org.springframework.aop.support.AopUtils;
import org.thepavel.jpqlbuilder.model.Company;
import org.thepavel.jpqlbuilder.model.Department;
import org.thepavel.jpqlbuilder.model.Employee;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class PathResolverTest {
  @Test
  public void propertyMap() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    Object value = new Object();
    pathResolver.put("name", value);

    assertSame(value, pathResolver.getValue("name"));
    assertNull(pathResolver.getValue("dummy"));
  }

  @Test
  public void childResolver() {
    PathResolver<Department> pathResolver = new PathResolver<>(Department.class, "a", context);
    PathResolver<Company> childResolver = pathResolver.createChildResolver(Company.class, "company");
    Company company = childResolver.getPathSpecifier();

    assertEquals("a.company", childResolver.getPropertyPath(company));
  }

  @Test
  @SuppressWarnings("rawtypes")
  public void childResolverForMapValue() {
    PathResolver<Map> pathResolver = new PathResolver<>(Map.class, "a.heads", context);
    PathResolver<Employee> childResolver = pathResolver.createChildResolverForMapValue(Employee.class);
    Employee employee = childResolver.getPathSpecifier();

    assertEquals("value(a.heads)", childResolver.getPropertyPath(employee));
  }

  @Test
  public void pathSpecifier() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    Company company = pathResolver.getPathSpecifier();

    assertNotNull(company);
    assertTrue(AopUtils.isAopProxy(company));
  }

  @Test
  public void rootPropertyPath() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    Company company = pathResolver.getPathSpecifier();

    assertEquals("a", pathResolver.getPropertyPath(company));
  }

  @Test
  public void ownPropertyPath() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    Company company = pathResolver.getPathSpecifier();

    assertEquals("a.name", pathResolver.getPropertyPath(company.getName()));
  }

  @Test
  public void childPropertyPath() {
    PathResolver<Department> pathResolver = new PathResolver<>(Department.class, "a", context);
    PathResolver<Company> childResolver = pathResolver.createChildResolver(Company.class, "company");
    Company company = childResolver.getPathSpecifier();

    assertEquals("a.company.status", pathResolver.getPropertyPath(company.getStatus()));
  }

  @Test
  public void unrecognizedPropertyPath() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);

    assertNull(pathResolver.getPropertyPath(new Object()));
  }

  @Test
  public void pathResolverForMapWithEntityValue() {
    Map<Object, Object> map = new HashMap<>();
    map.put(1L, new Company());

    PathResolver<Map<Object, Object>> pathResolver = new PathResolver<>(map, "a", context);
    Map<Object, Object> pathSpecifier = pathResolver.getPathSpecifier();

    assertNotSame(map, pathSpecifier);
    assertEquals(1, pathSpecifier.size());

    Object key = pathSpecifier.keySet().iterator().next();
    assertEquals(1L, key);

    Object value = pathSpecifier.get(key);
    assertTrue(AopUtils.isAopProxy(value));
    assertSame(Company.class, AopUtils.getTargetClass(value));
  }

  @Test
  public void pathResolverForMapWithNonEntityValue() {
    Map<Object, Object> map = new HashMap<>();
    map.put(1L, "dummy");

    PathResolver<Map<Object, Object>> pathResolver = new PathResolver<>(map, "a", context);
    Map<Object, Object> pathSpecifier = pathResolver.getPathSpecifier();

    assertNotSame(map, pathSpecifier);
    assertEquals(1, pathSpecifier.size());

    Object key = pathSpecifier.keySet().iterator().next();
    assertEquals(1L, key);

    Object value = pathSpecifier.get(key);
    assertFalse(AopUtils.isAopProxy(value));
    assertEquals("dummy", value);
  }

  @Test(expected = IllegalArgumentException.class)
  public void pathResolverForEmptyMap() {
    new PathResolver<>(new HashMap<>(), "a", context);
  }

  @Test
  public void resetPathSpecifier() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    Company a = pathResolver.getPathSpecifier();

    pathResolver.resetPathSpecifier(Company.class);
    Company b = pathResolver.getPathSpecifier();

    assertNotSame(a, b);
    assertTrue(AopUtils.isAopProxy(b));
  }

  private JpqlBuilderContext context;

  @Before
  public void setup() {
    context = JpqlBuilderContext.defaultContext();
  }
}
