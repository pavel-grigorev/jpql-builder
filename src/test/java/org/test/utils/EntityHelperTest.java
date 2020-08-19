package org.test.utils;

import org.junit.Test;
import org.test.entities.Advertiser;

import javax.persistence.Entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EntityHelperTest {
  @Test
  public void isEntity() {
    assertTrue(EntityHelper.isEntity(Advertiser.class));
    assertTrue(EntityHelper.isEntity(TestEntity.class));
    assertFalse(EntityHelper.isEntity(String.class));
  }

  @Test
  public void getEntityName() {
    assertEquals("test$Advertiser", EntityHelper.getEntityName(Advertiser.class));
    assertEquals("TestEntity", EntityHelper.getEntityName(TestEntity.class));
  }

  @Test(expected = NullPointerException.class)
  public void getEntityNameOfNonEntity() {
    EntityHelper.getEntityName(String.class);
  }

  @Entity
  private static class TestEntity {
  }
}
