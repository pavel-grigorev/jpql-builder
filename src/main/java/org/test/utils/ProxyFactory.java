package org.test.utils;

import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.ProxyFactoryBean;

public class ProxyFactory {
  public static <T> T createProxy(Class<T> type, Advice advice) {
    ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
    proxyFactoryBean.setAutodetectInterfaces(false);
    proxyFactoryBean.setTargetClass(type);
    proxyFactoryBean.addAdvice(advice);
    return type.cast(proxyFactoryBean.getObject());
  }

  @SuppressWarnings("unchecked")
  public static <T> T createProxy(T target, Advice advice) {
    ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
    proxyFactoryBean.setAutodetectInterfaces(false);
    proxyFactoryBean.setTarget(target);
    proxyFactoryBean.addAdvice(advice);
    return (T) proxyFactoryBean.getObject();
  }
}
