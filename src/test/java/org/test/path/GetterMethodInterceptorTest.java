package org.test.path;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.test.entities.AdGroup;
import org.test.entities.Advertiser;
import org.test.entities.Campaign;

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
    Method method = Campaign.class.getMethod("setName", String.class);
    interceptor.invoke(new TestMethodInvocation(method));

    assertTrue(interceptorSkipped);
  }

  @Test
  public void addsValueToPathResolver() throws Throwable {
    Method method = Campaign.class.getMethod("getName");
    Object value = interceptor.invoke(new TestMethodInvocation(method));

    assertNotNull(value);
    assertSame(value, pathResolver.getValue("name"));
    assertEquals("a.name", pathResolver.getPropertyPath(value));
  }

  @Test
  public void booleanGetter() throws Throwable {
    Method method = Campaign.class.getMethod("isActive");
    Object value = interceptor.invoke(new TestMethodInvocation(method));

    assertNotNull(value);
    assertSame(value, pathResolver.getValue("active"));
    assertEquals("a.active", pathResolver.getPropertyPath(value));
  }

  @Test
  public void multipleInvocationsOfTheSameMethod() throws Throwable {
    Method method = Campaign.class.getMethod("getName");
    Object value1 = interceptor.invoke(new TestMethodInvocation(method));
    Object value2 = interceptor.invoke(new TestMethodInvocation(method));

    assertNotNull(value1);
    assertNotNull(value2);
    assertSame(value1, value2);
  }

  @Test
  public void entityGetter() throws Throwable {
    Method method = Campaign.class.getMethod("getAdvertiser");
    Object value = interceptor.invoke(new TestMethodInvocation(method));

    assertNotNull(value);
    assertTrue(value instanceof Advertiser);
    assertEquals("a.advertiser", pathResolver.getPropertyPath(value));
  }

  @Test
  public void collectionGetter() throws Throwable {
    Method method = Campaign.class.getMethod("getAdGroups");
    Object value = interceptor.invoke(new TestMethodInvocation(method));

    assertNotNull(value);
    assertTrue(value instanceof List);
    assertEquals("a.adGroups", pathResolver.getPropertyPath(value));

    List<?> list = (List<?>) value;
    Object item = list.iterator().next();

    assertNotNull(item);
    assertTrue(item instanceof AdGroup);
  }

  private PathResolver<Campaign> pathResolver;
  private GetterMethodInterceptor interceptor;
  private boolean interceptorSkipped;

  @Before
  public void setup() {
    pathResolver = new PathResolver<>(Campaign.class, "a");
    interceptor = new GetterMethodInterceptor(pathResolver);
    interceptorSkipped = false;
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
