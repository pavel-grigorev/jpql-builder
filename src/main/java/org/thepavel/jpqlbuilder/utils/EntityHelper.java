/*
 * Copyright (c) 2020-2021 Pavel Grigorev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thepavel.jpqlbuilder.utils;

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
