package org.test.utils;

import org.junit.Test;
import org.test.DummyAdvice;
import org.test.factory.DefaultProxyFactory;
import org.test.factory.ProxyFactory;
import org.test.model.Company;

import javax.persistence.Entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EntityHelperTest {
  @Test
  public void isEntity() {
    assertTrue(EntityHelper.isEntity(Company.class));
    assertTrue(EntityHelper.isEntity(TestEntity.class));
    assertFalse(EntityHelper.isEntity(String.class));
  }

  @Test
  public void getEntityName() {
    assertEquals("test_Company", EntityHelper.getEntityName(Company.class));
    assertEquals("TestEntity", EntityHelper.getEntityName(TestEntity.class));
  }

  @Test(expected = NullPointerException.class)
  public void getEntityNameOfNonEntity() {
    EntityHelper.getEntityName(String.class);
  }

  @Test
  public void isProxiedEntity() {
    ProxyFactory factory = new DefaultProxyFactory();

    Company company = factory.createProxy(Company.class, new DummyAdvice());
    NonEntity nonEntity = factory.createProxy(NonEntity.class, new DummyAdvice());

    assertTrue(EntityHelper.isProxiedEntity(company));
    assertFalse(EntityHelper.isProxiedEntity(nonEntity));
    assertFalse(EntityHelper.isProxiedEntity(new Company()));
  }

  @Entity
  private static class TestEntity {
  }

  public static class NonEntity {
  }
}
