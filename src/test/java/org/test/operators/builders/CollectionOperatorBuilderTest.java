package org.test.operators.builders;

import org.junit.Test;
import org.test.operators.IsEmpty;
import org.test.operators.IsNotEmpty;
import org.test.operators.Operator;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.test.DummyJpqlStringWriter.asString;

public class CollectionOperatorBuilderTest {
  @Test
  public void isEmpty() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new CollectionOperatorBuilder<>(chain, new ArrayList<>()).isEmpty().getOperator();

    assertTrue(operator instanceof IsEmpty);
    assertEquals("[] is empty", asString(operator));
  }

  @Test
  public void isNotEmpty() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new CollectionOperatorBuilder<>(chain, new ArrayList<>()).isNotEmpty().getOperator();

    assertTrue(operator instanceof IsNotEmpty);
    assertEquals("[] is not empty", asString(operator));
  }
}
