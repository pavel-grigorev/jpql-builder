package org.test.path;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.test.utils.EntityHelper;
import org.test.utils.ReflectionHelper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
      Object instance = returnType.newInstance();
      return pathResolver
          .createChildResolver(instance, propertyName)
          .getPathSpecifier();
    }
    if (Collection.class.isAssignableFrom(returnType)) {
      Class<?> genericClass = ReflectionHelper.getGenericReturnType(method);
      Object instance = newInstance(genericClass);

      Collection<Object> collection = newCollectionInstance(returnType);
      collection.add(instance);

      return pathResolver
          .createChildResolver(collection, propertyName)
          .getPathSpecifier();
    }
    return newInstance(returnType);
  }

  private Object newInstance(Class<?> type) throws ReflectiveOperationException {
    return pathResolver.getContext().newInstance(type);
  }

  private Collection<Object> newCollectionInstance(Class<?> type) throws ReflectiveOperationException {
    return pathResolver.getContext().newCollectionInstance(type);
  }
}
