package org.test.path;

import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.test.JpqlBuilderContext;
import org.test.factory.DefaultCollectionInstanceFactory;
import org.test.factory.DefaultInstanceFactory;
import org.test.model.Company;
import org.test.model.Department;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class PathResolverTest {
  @Test
  public void propertyMap() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    Object value = new Object();
    pathResolver.put("name", value);

    assertSame(value, pathResolver.getValue("name"));
    assertNull(pathResolver.getValue("dummy"));
  }

  @Test
  public void childResolver() {
    PathResolver<Department> pathResolver = new PathResolver<>(Department.class, "a", context);
    PathResolver<Company> childResolver = pathResolver.createChildResolver(new Company(), "company");
    Company company = childResolver.getPathSpecifier();

    assertEquals("a.company", childResolver.getPropertyPath(company));
  }

  @Test
  public void pathSpecifier() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    Company company = pathResolver.getPathSpecifier();

    assertNotNull(company);
    assertTrue(AopUtils.isAopProxy(company));
  }

  @Test
  public void rootPropertyPath() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    Company company = pathResolver.getPathSpecifier();

    assertEquals("a", pathResolver.getPropertyPath(company));
  }

  @Test
  public void ownPropertyPath() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);
    Company company = pathResolver.getPathSpecifier();

    assertEquals("a.name", pathResolver.getPropertyPath(company.getName()));
  }

  @Test
  public void childPropertyPath() {
    PathResolver<Department> pathResolver = new PathResolver<>(Department.class, "a", context);
    PathResolver<Company> childResolver = pathResolver.createChildResolver(new Company(), "company");
    Company company = childResolver.getPathSpecifier();

    assertEquals("a.company.status", pathResolver.getPropertyPath(company.getStatus()));
  }

  @Test
  public void unrecognizedPropertyPath() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a", context);

    assertNull(pathResolver.getPropertyPath(new Object()));
  }

  private JpqlBuilderContext context;

  @Before
  public void setup() {
    context = new JpqlBuilderContext(new DefaultInstanceFactory(), new DefaultCollectionInstanceFactory());
  }
}
