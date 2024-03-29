/*
 * Copyright (c) 2020-2021 Pavel Grigorev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thepavel.jpqlbuilder.operators;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.thepavel.jpqlbuilder.DummyJpqlStringWriter.asString;
import static org.thepavel.jpqlbuilder.DummyOperator.dummy;

public class OperatorsTest {
  @Test
  public void and() {
    assertEquals("A and B", asString(new And<>("A", "B")));
  }

  @Test
  public void between() {
    assertEquals("A between B and C", asString(new Between<>("A", "B", "C")));
  }

  @Test
  public void equal() {
    assertEquals("A = B", asString(new Equal<>("A", "B")));
  }

  @Test
  public void equalsNull() {
    assertEquals("A is null", asString(new Equal<>("A", null)));
  }

  @Test
  public void greaterThan() {
    assertEquals("A > B", asString(new GreaterThan<>("A", "B")));
  }

  @Test
  public void greaterThanOrEqual() {
    assertEquals("A >= B", asString(new GreaterThanOrEqual<>("A", "B")));
  }

  @Test
  public void in() {
    assertEquals("A in [B, C]", asString(new In<>("A", Arrays.asList("B", "C"))));
  }

  @Test
  public void isNotNull() {
    assertEquals("A is not null", asString(new IsNotNull<>("A")));
  }

  @Test
  public void isNull() {
    assertEquals("A is null", asString(new IsNull<>("A")));
  }

  @Test
  public void lessThan() {
    assertEquals("A < B", asString(new LessThan<>("A", "B")));
  }

  @Test
  public void lessThanOrEqual() {
    assertEquals("A <= B", asString(new LessThanOrEqual<>("A", "B")));
  }

  @Test
  public void like() {
    assertEquals("A like B", asString(new Like("A", "B")));
  }

  @Test
  public void likeWithEscapeChar() {
    assertEquals("A like B escape C", asString(new Like("A", "B", "C")));
  }

  @Test
  public void not() {
    assertEquals("not (dummy(A))", asString(new Not(dummy("A"))));
  }

  @Test
  public void notBetween() {
    assertEquals("A not between B and C", asString(new NotBetween<>("A", "B", "C")));
  }

  @Test
  public void notEqual() {
    assertEquals("A <> B", asString(new NotEqual<>("A", "B")));
  }

  @Test
  public void notEqualNull() {
    assertEquals("A is not null", asString(new NotEqual<>("A", null)));
  }

  @Test
  public void notIn() {
    assertEquals("A not in [B, C]", asString(new NotIn<>("A", Arrays.asList("B", "C"))));
  }

  @Test
  public void notLike() {
    assertEquals("A not like B", asString(new NotLike("A", "B")));
  }

  @Test
  public void notLikeWithEscapeChar() {
    assertEquals("A not like B escape C", asString(new NotLike("A", "B", "C")));
  }

  @Test
  public void or() {
    assertEquals("A or B", asString(new Or<>("A", "B")));
  }

  @Test
  public void parentheses() {
    assertEquals("(dummy(A))", asString(new Parentheses(dummy("A"))));
  }

  @Test
  public void isEmpty() {
    assertEquals("[] is empty", asString(new IsEmpty(new ArrayList<>())));
  }

  @Test
  public void isNotEmpty() {
    assertEquals("[] is not empty", asString(new IsNotEmpty(new ArrayList<>())));
  }

  @Test
  public void memberOf() {
    assertEquals("A member of []", asString(new MemberOf<>("A", new ArrayList<>())));
  }

  @Test
  public void notMemberOf() {
    assertEquals("A not member of []", asString(new NotMemberOf<>("A", new ArrayList<>())));
  }
}
