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

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.jar.asm.Opcodes;
import org.thepavel.jpqlbuilder.path.PathResolver;
import org.thepavel.jpqlbuilder.utils.ReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.thepavel.jpqlbuilder.proxy.ProxyClassHelper.getProxyClassName;

public class ProxyClassBuilder {
  private static final String PATH_RESOLVER_FIELD = "jpqlBuilder$pathResolver";
  private static final int FIELD_MODIFIERS = Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL;
  private static final ByteBuddy BYTE_BUDDY = new ByteBuddy();

  static Method getterMethodHandler = GetterMethodHandler.HANDLER_METHOD;

  private ProxyClassBuilder() {
  }

  public static Class<?> buildProxyClass(Class<?> type) {
    Constructor<?> parentConstructor = ReflectionHelper.getDefaultConstructor(type);
    List<Method> getters = ReflectionHelper.getGetterMethods(type);

    Builder<?> builder = subclass(type);
    builder = definePathResolverField(builder);
    builder = defineConstructor(builder, parentConstructor);

    for (Method getter : getters) {
      builder = overrideGetter(builder, getter);
    }

    return makeAndLoadClass(builder);
  }

  private static Builder<?> subclass(Class<?> type) {
    return BYTE_BUDDY
        .subclass(type)
        .name(getProxyClassName(type));
  }

  private static Builder<?> definePathResolverField(Builder<?> builder) {
    return builder.defineField(PATH_RESOLVER_FIELD, PathResolver.class, FIELD_MODIFIERS);
  }

  private static Builder<?> defineConstructor(Builder<?> builder, Constructor<?> parentConstructor) {
    return builder
        .defineConstructor(Opcodes.ACC_PUBLIC)
        .withParameters(PathResolver.class)
        .intercept(
            MethodCall
                .invoke(parentConstructor)
                .andThen(FieldAccessor.ofField(PATH_RESOLVER_FIELD).setsArgumentAt(0))
        );
  }

  private static Builder<?> overrideGetter(Builder<?> builder, Method getter) {
    return builder
        .method(is(getter))
        .intercept(
            MethodCall
                .invoke(getterMethodHandler)
                .with(ReflectionHelper.getPropertyName(getter))
                .with(getter.getGenericReturnType())
                .withField(PATH_RESOLVER_FIELD)
                .withAssigner(Assigner.DEFAULT, Assigner.Typing.DYNAMIC)
        );
  }

  private static Class<?> makeAndLoadClass(Builder<?> builder) {
    return builder
        .make()
        .load(EntityProxyFactory.class.getClassLoader())
        .getLoaded();
  }
}
