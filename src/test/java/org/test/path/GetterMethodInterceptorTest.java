package org.test.path;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.test.model.Company;
import org.test.model.Department;
import org.test.model.Employee;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
    pathResolver = new PathResolver<>(Company.class, "a");
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

  private PathResolver<?> pathResolver;
  private GetterMethodInterceptor interceptor;
  private boolean interceptorSkipped;

  @Before
  public void setup() {
    interceptorSkipped = false;
  }

  private Object invoke(Class<?> type, String methodName, Class<?>... params) throws Throwable {
    pathResolver = new PathResolver<>(type, "a");
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
