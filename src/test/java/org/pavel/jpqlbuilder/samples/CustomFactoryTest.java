package org.pavel.jpqlbuilder.samples;

import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.pavel.jpqlbuilder.factory.DefaultMapInstanceFactory;
import org.pavel.jpqlbuilder.factory.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.pavel.jpqlbuilder.JpqlBuilder;
import org.pavel.jpqlbuilder.factory.DefaultCollectionInstanceFactory;
import org.pavel.jpqlbuilder.factory.DefaultInstanceFactory;
import org.pavel.jpqlbuilder.model.Company;
import org.pavel.jpqlbuilder.model.Department;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
  public void customMapInstanceFactory() {
    DefaultMapInstanceFactory mapInstanceFactory = new DefaultMapInstanceFactory();
    mapInstanceFactory.add(Map.class, TreeMap::new);

    JpqlBuilder<Company> builder = JpqlBuilder.with(mapInstanceFactory).select(Company.class);
    Company company = builder.getPathSpecifier();

    assertTrue(AopUtils.isAopProxy(company));
    assertEquals("", company.getName());
    assertTrue(company.getHeads() instanceof TreeMap);
    assertTrue(company.getDepartments() instanceof ArrayList);
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
