package org.test.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ObjectFactory {
  public static Object newInstance(Class<?> returnType) throws ReflectiveOperationException {
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

  public static <T> Collection<T> newCollectionInstance(Class<?> type) {
    if (!Collection.class.isAssignableFrom(type)) {
      throw new IllegalArgumentException("Class " + type + " is not a collection class");
    }
    if (List.class.isAssignableFrom(type)) {
      return new ArrayList<>();
    }
    if (Set.class.isAssignableFrom(type)) {
      return new HashSet<>();
    }
    throw new IllegalArgumentException("Collection type " + type + " is unsupported");
  }
}
