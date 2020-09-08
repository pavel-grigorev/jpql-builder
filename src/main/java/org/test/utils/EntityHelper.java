package org.test.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;

import javax.persistence.Entity;

public class EntityHelper {
  public static boolean isEntity(Class<?> type) {
    return type.isAnnotationPresent(Entity.class);
  }

  public static String getEntityName(Class<?> type) {
    String name = type.getAnnotation(Entity.class).name();
    return StringUtils.isNotBlank(name) ? name : type.getSimpleName();
  }

  public static boolean isProxiedEntity(Object object) {
    return AopUtils.isAopProxy(object) && isEntity(AopUtils.getTargetClass(object));
  }
}
