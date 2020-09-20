package org.pavel.jpqlbuilder.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AliasGeneratorTest {
  @Test
  public void test() {
    AliasGenerator aliasGenerator = new AliasGenerator();

    assertEquals("a", aliasGenerator.next());
    assertEquals("b", aliasGenerator.next());
    assertEquals("c", aliasGenerator.next());
    assertEquals("d", aliasGenerator.next());
    assertEquals("e", aliasGenerator.next());
    assertEquals("f", aliasGenerator.next());
    assertEquals("g", aliasGenerator.next());
    assertEquals("h", aliasGenerator.next());
    assertEquals("i", aliasGenerator.next());
    assertEquals("j", aliasGenerator.next());
    assertEquals("k", aliasGenerator.next());
    assertEquals("l", aliasGenerator.next());
    assertEquals("m", aliasGenerator.next());
    assertEquals("n", aliasGenerator.next());
    assertEquals("o", aliasGenerator.next());
    assertEquals("p", aliasGenerator.next());
    assertEquals("q", aliasGenerator.next());
    assertEquals("r", aliasGenerator.next());
    assertEquals("s", aliasGenerator.next());
    assertEquals("t", aliasGenerator.next());
    assertEquals("u", aliasGenerator.next());
    assertEquals("v", aliasGenerator.next());
    assertEquals("w", aliasGenerator.next());
    assertEquals("x", aliasGenerator.next());
    assertEquals("y", aliasGenerator.next());
    assertEquals("z", aliasGenerator.next());

    assertEquals("aa", aliasGenerator.next());
    assertEquals("ab", aliasGenerator.next());
    assertEquals("ac", aliasGenerator.next());
    assertEquals("ad", aliasGenerator.next());
    assertEquals("ae", aliasGenerator.next());
    assertEquals("af", aliasGenerator.next());
    assertEquals("ag", aliasGenerator.next());
    assertEquals("ah", aliasGenerator.next());
    assertEquals("ai", aliasGenerator.next());
    assertEquals("aj", aliasGenerator.next());
    assertEquals("ak", aliasGenerator.next());
    assertEquals("al", aliasGenerator.next());
    assertEquals("am", aliasGenerator.next());
    assertEquals("an", aliasGenerator.next());
    assertEquals("ao", aliasGenerator.next());
    assertEquals("ap", aliasGenerator.next());
    assertEquals("aq", aliasGenerator.next());
    assertEquals("ar", aliasGenerator.next());
    assertEquals("as", aliasGenerator.next());
    assertEquals("at", aliasGenerator.next());
    assertEquals("au", aliasGenerator.next());
    assertEquals("av", aliasGenerator.next());
    assertEquals("aw", aliasGenerator.next());
    assertEquals("ax", aliasGenerator.next());
    assertEquals("ay", aliasGenerator.next());
    assertEquals("az", aliasGenerator.next());

    assertEquals("ba", aliasGenerator.next());
  }
}
