package org.test.path;

import org.junit.Test;
import org.test.entities.Campaign;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PathResolverListTest {
  @Test
  public void propertyPath() {
    PathResolver<Campaign> pathResolver = new PathResolver<>(Campaign.class, "a");
    Campaign campaign = pathResolver.getPathSpecifier();

    PathResolverList list = new PathResolverList();
    list.add(pathResolver);

    assertEquals("a.name", list.getPropertyPath(campaign.getName()));
  }

  @Test
  public void unrecognizedPropertyPath() {
    PathResolverList list = new PathResolverList();
    list.add(new PathResolver<>(Campaign.class, "a"));

    assertNull(list.getPropertyPath(new Object()));
  }
}
