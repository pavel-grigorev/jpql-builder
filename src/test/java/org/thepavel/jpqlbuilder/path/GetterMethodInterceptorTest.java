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

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.thepavel.jpqlbuilder.JpqlBuilderContext;
import org.thepavel.jpqlbuilder.model.Department;
import org.thepavel.jpqlbuilder.model.Employee;
import org.thepavel.jpqlbuilder.model.Company;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class GetterMethodInterceptorTest {
  @Test
  public void skipsNonGetterMethods() throws Throwable {
    invoke(Company.class, "setName", String.class);

    assertTrue(interceptorSkipped);
  }

  @Test
  public void getter() throws Throwable {
    Object value = invoke(Company.class, "getName");

    assertNotNull(value);
    assertTrue(value instanceof String);
    assertSame(value, pathResolver.getValue("name"));
    assertEquals("a.name", pathResolver.getPropertyPath(value));
  }

  @Test
  public void booleanGetter() throws Throwable {
    Object value = invoke(Employee.class, "isHeadOfDepartment");

    assertNotNull(value);
    assertTrue(value instanceof Boolean);
    assertSame(value, pathResolver.getValue("headOfDepartment"));
    assertEquals("a.headOfDepartment", pathResolver.getPropertyPath(value));
  }

  @Test
  public void multipleInvocationsOfTheSameMethod() throws Throwable {
    pathResolver = new PathResolver<>(Company.class, "a", context);
    interceptor = new GetterMethodInterceptor(pathResolver);

    Method method = Company.class.getMethod("getName");
    Object value1 = interceptor.invoke(new TestMethodInvocation(method));
    Object value2 = interceptor.invoke(new TestMethodInvocation(method));

    assertNotNull(value1);
    assertNotNull(value2);
    assertSame(value1, value2);
  }

  @Test
  public void entityGetter() throws Throwable {
    Object value = invoke(Department.class, "getCompany");

    assertNotNull(value);
    assertTrue(value instanceof Company);
    assertSame(value, pathResolver.getValue("company"));
    assertEquals("a.company", pathResolver.getPropertyPath(value));
  }

  @Test
  public void collectionGetter() throws Throwable {
    Object value = invoke(Company.class, "getDepartments");

    assertNotNull(value);
    assertTrue(value instanceof List);
    assertSame(value, pathResolver.getValue("departments"));
    assertEquals("a.departments", pathResolver.getPropertyPath(value));

    List<?> list = (List<?>) value;
    Object item = list.iterator().next();

    assertNotNull(item);
    assertTrue(item instanceof Department);
  }

  @Test
  public void mapGetter() throws Throwable {
    Object value = invoke(Company.class, "getHeads");

    assertNotNull(value);
    assertTrue(value instanceof Map);
    assertSame(value, pathResolver.getValue("heads"));
    assertEquals("a.heads", pathResolver.getPropertyPath(value));

    Map<?, ?> map = (Map<?, ?>) value;
    assertFalse(map.isEmpty());

    Object key = map.keySet().iterator().next();
    assertTrue(key instanceof Long);
    assertTrue(map.get(key) instanceof Employee);
  }

  private JpqlBuilderContext context;
  private PathResolver<?> pathResolver;
  private GetterMethodInterceptor interceptor;
  private boolean interceptorSkipped;

  @Before
  public void setup() {
    context = JpqlBuilderContext.defaultContext();
    interceptorSkipped = false;
  }

  private Object invoke(Class<?> type, String methodName, Class<?>... params) throws Throwable {
    pathResolver = new PathResolver<>(type, "a", context);
    interceptor = new GetterMethodInterceptor(pathResolver);

    Method method = type.getMethod(methodName, params);
    return interceptor.invoke(new TestMethodInvocation(method));
  }

  private class TestMethodInvocation implements MethodInvocation {
    private final Method method;

    private TestMethodInvocation(Method method) {
      this.method = method;
    }

    @Override
    public Method getMethod() {
      return method;
    }

    @Override
    public Object[] getArguments() {
      return new Object[0];
    }

    @Override
    public Object proceed() {
      interceptorSkipped = true;
      return null;
    }

    @Override
    public Object getThis() {
      return null;
    }

    @Override
    public AccessibleObject getStaticPart() {
      return null;
    }
  }
}
