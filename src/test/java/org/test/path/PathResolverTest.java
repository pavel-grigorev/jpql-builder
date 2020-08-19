package org.test.path;

import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.test.entities.Advertiser;
import org.test.entities.Campaign;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class PathResolverTest {
  @Test
  public void propertyMap() {
    PathResolver<Campaign> pathResolver = new PathResolver<>(Campaign.class, "a");
    Object value = new Object();
    pathResolver.put("name", value);

    assertSame(value, pathResolver.getValue("name"));
    assertNull(pathResolver.getValue("dummy"));
  }

  @Test
  public void childResolver() {
    PathResolver<Campaign> pathResolver = new PathResolver<>(Campaign.class, "a");
    PathResolver<Advertiser> childResolver = pathResolver.createChildResolver(new Advertiser(), "advertiser");
    Advertiser advertiser = childResolver.getPathSpecifier();

    assertEquals("a.advertiser", childResolver.getPropertyPath(advertiser));
  }

  @Test
  public void pathSpecifier() {
    PathResolver<Campaign> pathResolver = new PathResolver<>(Campaign.class, "a");
    Campaign campaign = pathResolver.getPathSpecifier();

    assertNotNull(campaign);
    assertTrue(AopUtils.isAopProxy(campaign));
  }

  @Test
  public void rootPropertyPath() {
    PathResolver<Campaign> pathResolver = new PathResolver<>(Campaign.class, "a");
    Campaign campaign = pathResolver.getPathSpecifier();

    assertEquals("a", pathResolver.getPropertyPath(campaign));
  }

  @Test
  public void ownPropertyPath() {
    PathResolver<Campaign> pathResolver = new PathResolver<>(Campaign.class, "a");
    Campaign campaign = pathResolver.getPathSpecifier();

    assertEquals("a.name", pathResolver.getPropertyPath(campaign.getName()));
  }

  @Test
  public void childPropertyPath() {
    PathResolver<Campaign> pathResolver = new PathResolver<>(Campaign.class, "a");
    PathResolver<Advertiser> childResolver = pathResolver.createChildResolver(new Advertiser(), "advertiser");
    Advertiser advertiser = childResolver.getPathSpecifier();

    assertEquals("a.advertiser.status", pathResolver.getPropertyPath(advertiser.getStatus()));
  }

  @Test
  public void unrecognizedPropertyPath() {
    PathResolver<Campaign> pathResolver = new PathResolver<>(Campaign.class, "a");

    assertNull(pathResolver.getPropertyPath(new Object()));
  }
}
