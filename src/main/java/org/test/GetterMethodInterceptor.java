package org.test;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.test.utils.EntityHelper;
import org.test.utils.EnumFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GetterMethodInterceptor implements MethodInterceptor {
  private static final List<String> PREFIXES = Arrays.asList("get", "is");

  private final PathResolver<?> pathResolver;

  GetterMethodInterceptor(PathResolver<?> pathResolver) {
    this.pathResolver = pathResolver;
  }

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    Method method = invocation.getMethod();

    if (!isGetter(method)) {
      return invocation.proceed();
    }

    String propertyName = getPropertyName(method);
    Object value = pathResolver.getValue(propertyName);
    if (value != null) {
      return value;
    }

    value = getReturnValue(method, propertyName);
    pathResolver.put(propertyName, value);
    return value;
  }

  private static boolean isGetter(Method method) {
    return getPrefix(method).isPresent() &&
        hasNoParameters(method) &&
        isNotStatic(method);
  }

  private static Optional<String> getPrefix(Method method) {
    String name = method.getName();
    return PREFIXES
        .stream()
        .filter(name::startsWith)
        .findFirst();
  }

  private static boolean hasNoParameters(Method method) {
    return method.getParameterTypes().length == 0;
  }

  private static boolean isNotStatic(Method method) {
    return !Modifier.isStatic(method.getModifiers());
  }

  private static String getPropertyName(Method method) {
    int offset = getPrefix(method)
        .map(String::length)
        .orElse(0);

    String propertyName = method.getName().substring(offset);
    return StringUtils.uncapitalize(propertyName);
  }

  private Object getReturnValue(Method method, String propertyName) throws ReflectiveOperationException {
    Class<?> returnType = method.getReturnType();

    if (EntityHelper.isEntity(returnType)) {
      return pathResolver
          .createChildResolver(returnType, propertyName)
          .getPathSpecifier();
    }
    if (returnType.isEnum()) {
      return EnumFactory.newInstance(returnType);
    }
    if (Byte.class.isAssignableFrom(returnType)) {
      return new Byte(Byte.MAX_VALUE);
    }
    if (Short.class.isAssignableFrom(returnType)) {
      return new Short(Short.MAX_VALUE);
    }
    if (Integer.class.isAssignableFrom(returnType)) {
      return new Integer(0);
    }
    if (Long.class.isAssignableFrom(returnType)) {
      return new Long(0);
    }
    if (Float.class.isAssignableFrom(returnType)) {
      return new Float(0);
    }
    if (Double.class.isAssignableFrom(returnType)) {
      return new Double(0);
    }
    if (Boolean.class.isAssignableFrom(returnType)) {
      return new Boolean(false);
    }
    if (Character.class.isAssignableFrom(returnType)) {
      return new Character('0');
    }
    if (BigDecimal.class.isAssignableFrom(returnType)) {
      return new BigDecimal(0);
    }
    if (UUID.class.isAssignableFrom(returnType)) {
      return UUID.randomUUID();
    }
    return returnType.newInstance();
  }
}
