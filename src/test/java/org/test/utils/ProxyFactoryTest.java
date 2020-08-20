package org.test.utils;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.test.model.Company;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class ProxyFactoryTest {
  @Test
  public void classProxy() {
    Company proxy = ProxyFactory.createProxy(Company.class, new DummyAdvice());

    assertNotNull(proxy);
    assertTrue(AopUtils.isAopProxy(proxy));
  }

  @Test
  public void instanceProxy() {
    Company company = new Company();
    Company proxy = ProxyFactory.createProxy(company, new DummyAdvice());

    assertNotNull(proxy);
    assertNotSame(company, proxy);
    assertTrue(AopUtils.isAopProxy(proxy));
  }

  private static class DummyAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) {
      return null;
    }
  }
}
