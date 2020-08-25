package org.test.factory;

import org.aopalliance.aop.Advice;

public interface ProxyFactory {
  <T> T createProxy(Class<T> type, Advice advice);
  <T> T createProxy(T target, Advice advice);
}
