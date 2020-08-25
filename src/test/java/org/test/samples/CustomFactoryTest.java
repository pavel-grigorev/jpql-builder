package org.test.samples;

import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.test.JpqlBuilder;
import org.test.factory.DefaultCollectionInstanceFactory;
import org.test.factory.DefaultInstanceFactory;
import org.test.factory.ProxyFactory;
import org.test.model.Department;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CustomFactoryTest {
  @Test
  public void defaultFactories() {
    JpqlBuilder<Department> builder = JpqlBuilder.select(Department.class);
    Department department = builder.getPathSpecifier();

    assertTrue(AopUtils.isAopProxy(department));
    assertEquals("", department.getName());
    assertTrue(department.getEmployees() instanceof ArrayList);
    assertTrue(AopUtils.isAopProxy(department.getCompany()));
  }

  @Test
  public void customInstanceFactory() {
    DefaultInstanceFactory instanceFactory = new DefaultInstanceFactory();
    instanceFactory.add(String.class, () -> "test");

    JpqlBuilder<Department> builder = JpqlBuilder.with(instanceFactory).select(Department.class);
    Department department = builder.getPathSpecifier();

    assertTrue(AopUtils.isAopProxy(department));
    assertEquals("test", department.getName());
    assertTrue(department.getEmployees() instanceof ArrayList);
    assertTrue(AopUtils.isAopProxy(department.getCompany()));
  }

  @Test
  public void customCollectionInstanceFactory() {
    DefaultCollectionInstanceFactory collectionInstanceFactory = new DefaultCollectionInstanceFactory();
    collectionInstanceFactory.add(List.class, Vector::new);

    JpqlBuilder<Department> builder = JpqlBuilder.with(collectionInstanceFactory).select(Department.class);
    Department department = builder.getPathSpecifier();

    assertTrue(AopUtils.isAopProxy(department));
    assertEquals("", department.getName());
    assertTrue(department.getEmployees() instanceof Vector);
    assertTrue(AopUtils.isAopProxy(department.getCompany()));
  }

  @Test
  public void customProxyFactory() {
    JpqlBuilder<Department> builder = JpqlBuilder.with(new TestProxyFactory()).select(Department.class);
    Department department = builder.getPathSpecifier();

    assertFalse(AopUtils.isAopProxy(department));
    assertNull(department.getName());
    assertNull(department.getEmployees());
    assertNull(department.getCompany());
  }

  private static class TestProxyFactory implements ProxyFactory {
    @Override
    public <T> T createProxy(Class<T> type, Advice advice) {
      try {
        return type.newInstance();
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e.getMessage(), e);
      }
    }

    @Override
    public <T> T createProxy(T target, Advice advice) {
      return target;
    }
  }
}
