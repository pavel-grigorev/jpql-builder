package org.test.factory;

import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.ProxyFactoryBean;

public class DefaultProxyFactory implements ProxyFactory {
  @Override
  public <T> T createProxy(Class<T> type, Advice advice) {
    ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
    proxyFactoryBean.setAutodetectInterfaces(false);
    proxyFactoryBean.setTargetClass(type);
    proxyFactoryBean.addAdvice(advice);
    return type.cast(proxyFactoryBean.getObject());
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T createProxy(T target, Advice advice) {
    ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
    proxyFactoryBean.setAutodetectInterfaces(false);
    proxyFactoryBean.setTarget(target);
    proxyFactoryBean.addAdvice(advice);
    return (T) proxyFactoryBean.getObject();
  }
}
