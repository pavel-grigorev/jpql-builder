package org.test.path;

import org.junit.Before;
import org.junit.Test;
import org.test.JpqlBuilderContext;
import org.test.model.Company;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PathResolverListTest {
  @Test
  public void propertyPath() {
    Company company = pathResolver.getPathSpecifier();

    assertEquals("a.name", list.getPropertyPath(company.getName()));
  }

  @Test
  public void unrecognizedPropertyPath() {
    assertNull(list.getPropertyPath(new Object()));
  }

  private PathResolver<Company> pathResolver;
  private PathResolverList list;

  @Before
  public void setup() {
    pathResolver = new PathResolver<>(Company.class, "a", JpqlBuilderContext.defaultContext());

    list = new PathResolverList();
    list.add(pathResolver);
  }
}
