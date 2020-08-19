package org.test.utils;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.test.entities.Advertiser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

public class ProxyFactoryTest {
  @Test
  public void classProxy() {
    Advertiser proxy = ProxyFactory.createProxy(Advertiser.class, new DummyAdvice());

    assertNotNull(proxy);
    assertTrue(AopUtils.isAopProxy(proxy));
  }

  @Test
  public void instanceProxy() {
    Advertiser advertiser = new Advertiser();
    Advertiser proxy = ProxyFactory.createProxy(advertiser, new DummyAdvice());

    assertNotNull(proxy);
    assertNotSame(advertiser, proxy);
    assertTrue(AopUtils.isAopProxy(proxy));
  }

  private static class DummyAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) {
      return null;
    }
  }
}
