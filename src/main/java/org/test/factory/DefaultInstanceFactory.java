package org.test.factory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DefaultInstanceFactory implements InstanceFactory {
  private final Map<Class<?>, InstanceCreator<?>> instanceCreators = new HashMap<>();
  private final EnumFactory enumFactory;

  public DefaultInstanceFactory() {
    this(new EnumFactory());
  }

  public DefaultInstanceFactory(EnumFactory enumFactory) {
    this.enumFactory = enumFactory;
    initInstanceCreators();
  }

  private void initInstanceCreators() {
    instanceCreators.put(Byte.class, Primitives::newByte);
    instanceCreators.put(Short.class, Primitives::newShort);
    instanceCreators.put(Integer.class, Primitives::newInteger);
    instanceCreators.put(Long.class, Primitives::newLong);
    instanceCreators.put(Float.class, Primitives::newFloat);
    instanceCreators.put(Double.class, Primitives::newDouble);
    instanceCreators.put(Boolean.class, Primitives::newBoolean);
    instanceCreators.put(Character.class, Primitives::newCharacter);
    instanceCreators.put(BigDecimal.class, () -> new BigDecimal(0));
    instanceCreators.put(UUID.class, UUID::randomUUID);
  }

  public <T> void add(Class<T> type, InstanceCreator<T> instanceCreator) {
    instanceCreators.put(type, instanceCreator);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T newInstance(Class<T> returnType) throws ReflectiveOperationException {
    if (returnType.isEnum()) {
      return (T) enumFactory.newInstance(returnType);
    }
    return (T) instanceCreators
        .getOrDefault(returnType, returnType::newInstance)
        .newInstance();
  }
}
