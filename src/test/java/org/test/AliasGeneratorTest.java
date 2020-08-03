package org.test;

import org.junit.Assert;
import org.junit.Test;

public class AliasGeneratorTest {
  @Test
  public void test() {
    AliasGenerator aliasGenerator = new AliasGenerator();

    Assert.assertEquals("a", aliasGenerator.next());
    Assert.assertEquals("b", aliasGenerator.next());
    Assert.assertEquals("c", aliasGenerator.next());
    Assert.assertEquals("d", aliasGenerator.next());
    Assert.assertEquals("e", aliasGenerator.next());
    Assert.assertEquals("f", aliasGenerator.next());
    Assert.assertEquals("g", aliasGenerator.next());
    Assert.assertEquals("h", aliasGenerator.next());
    Assert.assertEquals("i", aliasGenerator.next());
    Assert.assertEquals("j", aliasGenerator.next());
    Assert.assertEquals("k", aliasGenerator.next());
    Assert.assertEquals("l", aliasGenerator.next());
    Assert.assertEquals("m", aliasGenerator.next());
    Assert.assertEquals("n", aliasGenerator.next());
    Assert.assertEquals("o", aliasGenerator.next());
    Assert.assertEquals("p", aliasGenerator.next());
    Assert.assertEquals("q", aliasGenerator.next());
    Assert.assertEquals("r", aliasGenerator.next());
    Assert.assertEquals("s", aliasGenerator.next());
    Assert.assertEquals("t", aliasGenerator.next());
    Assert.assertEquals("u", aliasGenerator.next());
    Assert.assertEquals("v", aliasGenerator.next());
    Assert.assertEquals("w", aliasGenerator.next());
    Assert.assertEquals("x", aliasGenerator.next());
    Assert.assertEquals("y", aliasGenerator.next());
    Assert.assertEquals("z", aliasGenerator.next());

    Assert.assertEquals("aa", aliasGenerator.next());
    Assert.assertEquals("ab", aliasGenerator.next());
    Assert.assertEquals("ac", aliasGenerator.next());
    Assert.assertEquals("ad", aliasGenerator.next());
    Assert.assertEquals("ae", aliasGenerator.next());
    Assert.assertEquals("af", aliasGenerator.next());
    Assert.assertEquals("ag", aliasGenerator.next());
    Assert.assertEquals("ah", aliasGenerator.next());
    Assert.assertEquals("ai", aliasGenerator.next());
    Assert.assertEquals("aj", aliasGenerator.next());
    Assert.assertEquals("ak", aliasGenerator.next());
    Assert.assertEquals("al", aliasGenerator.next());
    Assert.assertEquals("am", aliasGenerator.next());
    Assert.assertEquals("an", aliasGenerator.next());
    Assert.assertEquals("ao", aliasGenerator.next());
    Assert.assertEquals("ap", aliasGenerator.next());
    Assert.assertEquals("aq", aliasGenerator.next());
    Assert.assertEquals("ar", aliasGenerator.next());
    Assert.assertEquals("as", aliasGenerator.next());
    Assert.assertEquals("at", aliasGenerator.next());
    Assert.assertEquals("au", aliasGenerator.next());
    Assert.assertEquals("av", aliasGenerator.next());
    Assert.assertEquals("aw", aliasGenerator.next());
    Assert.assertEquals("ax", aliasGenerator.next());
    Assert.assertEquals("ay", aliasGenerator.next());
    Assert.assertEquals("az", aliasGenerator.next());

    Assert.assertEquals("ba", aliasGenerator.next());
  }
}
