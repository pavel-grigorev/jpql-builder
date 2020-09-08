package org.test.factory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Currency;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.SimpleTimeZone;
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
    instanceCreators.put(BigInteger.class, () -> BigInteger.valueOf(128));
    instanceCreators.put(UUID.class, UUID::randomUUID);
    instanceCreators.put(Date.class, () -> new Date(0));
    instanceCreators.put(GregorianCalendar.class, GregorianCalendar::new);
    instanceCreators.put(Locale.class, () -> new Locale("en"));
    instanceCreators.put(SimpleTimeZone.class, () -> new SimpleTimeZone(0, ""));
    instanceCreators.put(Currency.class, CurrencyFactory::newInstance);
    instanceCreators.put(byte[].class, () -> new byte[0]);
    instanceCreators.put(HashMap.class, HashMap::new);
  }

  public <T> void add(Class<T> type, InstanceCreator<T> instanceCreator) {
    instanceCreators.put(type, instanceCreator);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T newInstance(Class<T> type) throws ReflectiveOperationException {
    if (type.isEnum()) {
      return (T) enumFactory.newInstance(type);
    }

    Optional<InstanceCreator<?>> instanceCreator = getInstanceCreator(type);
    if (instanceCreator.isPresent()) {
      return (T) instanceCreator.get().newInstance();
    }

    return type.newInstance();
  }

  private Optional<InstanceCreator<?>> getInstanceCreator(Class<?> type) {
    return instanceCreators
        .keySet()
        .stream()
        .filter(type::isAssignableFrom)
        .findFirst()
        .map(instanceCreators::get);
  }
}
