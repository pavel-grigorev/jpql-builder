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

package org.thepavel.jpqlbuilder.path;

import org.thepavel.jpqlbuilder.JpqlBuilderContext;
import org.thepavel.jpqlbuilder.proxy.EntityProxyFactory;
import org.thepavel.jpqlbuilder.utils.ReflectionHelper;
import org.thepavel.jpqlbuilder.utils.EntityHelper;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class PathResolver<T> {
  private final Map<String, Object> nameToValue = new HashMap<>();
  private final Map<Object, String> valueToName = new IdentityHashMap<>();
  private final PathResolverList children = new PathResolverList();
  private final String basePath;
  private T pathSpecifier;
  private final JpqlBuilderContext context;

  public PathResolver(Class<T> entityClass, String basePath, JpqlBuilderContext context) {
    this.basePath = basePath;
    this.context = context;
    resetPathSpecifier(entityClass);
  }

  @SuppressWarnings("unchecked")
  public PathResolver(Map<Object, Object> target, String basePath, JpqlBuilderContext context) {
    if (target == null || target.isEmpty()) {
      throw new IllegalArgumentException("can not create a path resolver for a null or empty map");
    }

    this.basePath = basePath;
    this.context = context;

    Map.Entry<Object, Object> entry = target.entrySet().iterator().next();
    Object key = entry.getKey();
    Object value = entry.getValue();
    Class<?> valueType = value.getClass();

    if (EntityHelper.isEntity(valueType)) {
      value = createChildResolverForMapValue(valueType).getPathSpecifier();
    }

    Map<Object, Object> clone = ReflectionHelper.newInstance(target.getClass());
    clone.put(key, value);

    this.pathSpecifier = (T) clone;
  }

  public void resetPathSpecifier(Class<T> entityClass) {
    pathSpecifier = EntityProxyFactory.createProxy(entityClass, this);
  }

  public JpqlBuilderContext getContext() {
    return context;
  }

  public Object getValue(String propertyName) {
    return nameToValue.get(propertyName);
  }

  public void put(String propertyName, Object value) {
    nameToValue.put(propertyName, value);
    valueToName.put(value, propertyName);
  }

  public <E> PathResolver<E> createChildResolver(Class<E> entityClass, String propertyName) {
    PathResolver<E> pathResolver = new PathResolver<>(entityClass, buildPath(propertyName), context);
    children.add(pathResolver);
    return pathResolver;
  }

  private String buildPath(String propertyName) {
    return basePath + '.' + propertyName;
  }

  <E> PathResolver<E> createChildResolverForMapValue(Class<E> entityClass) {
    PathResolver<E> pathResolver = new PathResolver<>(entityClass, buildMapValueAlias(), context);
    children.add(pathResolver);
    return pathResolver;
  }

  private String buildMapValueAlias() {
    return "value(" + basePath + ")";
  }

  public T getPathSpecifier() {
    return pathSpecifier;
  }

  public String getPropertyPath(Object value) {
    if (value == pathSpecifier) {
      return basePath;
    }
    String propertyPath = getPropertyPathInternal(value);
    return propertyPath != null ? propertyPath : children.getPropertyPath(value);
  }

  private String getPropertyPathInternal(Object value) {
    String propertyName = valueToName.get(value);
    return propertyName != null ? buildPath(propertyName) : null;
  }
}
