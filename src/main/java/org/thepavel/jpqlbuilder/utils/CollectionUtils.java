package org.thepavel.jpqlbuilder.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionUtils {
  public static List<Object> toList(Object[] array) {
    return Arrays.stream(array).collect(Collectors.toList());
  }
}
