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

package org.thepavel.jpqlbuilder.proxy;

import org.thepavel.jpqlbuilder.utils.ReflectionHelper;

public class ProxyClassHelper {
  private static final String SUFFIX = "$JpqlBuilder";

  private ProxyClassHelper() {
  }

  public static String getProxyClassName(Class<?> type) {
    return type.getName() + SUFFIX;
  }

  public static boolean isProxyClass(Class<?> type) {
    return type.getName().endsWith(SUFFIX);
  }

  public static Class<?> getTargetClass(Class<?> type) {
    return ReflectionHelper.getClass(getTargetClassName(type));
  }

  private static String getTargetClassName(Class<?> type) {
    String className = type.getName();
    return className.substring(0, className.indexOf(SUFFIX));
  }
}
