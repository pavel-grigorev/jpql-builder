package org.test;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.test.model.Company;

import java.util.List;

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

  private static class DummyAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) {
      return null;
    }
  }
}
