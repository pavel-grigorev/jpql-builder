package org.thepavel.jpqlbuilder;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DummyAdvice implements MethodInterceptor {
  @Override
  public Object invoke(MethodInvocation invocation) {
    return null;
  }
}
