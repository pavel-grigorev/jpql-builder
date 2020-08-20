package org.test.path;

import org.junit.Test;
import org.test.model.Company;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PathResolverListTest {
  @Test
  public void propertyPath() {
    PathResolver<Company> pathResolver = new PathResolver<>(Company.class, "a");
    Company company = pathResolver.getPathSpecifier();

    PathResolverList list = new PathResolverList();
    list.add(pathResolver);

    assertEquals("a.name", list.getPropertyPath(company.getName()));
  }

  @Test
  public void unrecognizedPropertyPath() {
    PathResolverList list = new PathResolverList();
    list.add(new PathResolver<>(Company.class, "a"));

    assertNull(list.getPropertyPath(new Object()));
  }
}
