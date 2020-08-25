package org.test.factory;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.test.model.Company;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class DefaultProxyFactoryTest {
  @Test
  public void classProxy() {
    Company proxy = factory.createProxy(Company.class, new DummyAdvice());

    assertNotNull(proxy);
    assertTrue(AopUtils.isAopProxy(proxy));
  }

  @Test
  public void instanceProxy() {
    Company company = new Company();
    Company proxy = factory.createProxy(company, new DummyAdvice());

    assertNotNull(proxy);
    assertNotSame(company, proxy);
    assertTrue(AopUtils.isAopProxy(proxy));
  }

  private ProxyFactory factory;

  @Before
  public void setup() {
    factory = new DefaultProxyFactory();
  }

  private static class DummyAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) {
      return null;
    }
  }
}
