package org.thepavel.jpqlbuilder.factory;

import org.aopalliance.aop.Advice;

public interface ProxyFactory {
  <T> T createProxy(Class<T> type, Advice advice);
  <T> T createProxy(T target, Advice advice);
}
