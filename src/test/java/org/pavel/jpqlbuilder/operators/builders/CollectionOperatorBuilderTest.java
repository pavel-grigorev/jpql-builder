package org.pavel.jpqlbuilder.operators.builders;

import org.junit.Assert;
import org.junit.Test;
import org.pavel.jpqlbuilder.DummyJpqlStringWriter;
import org.pavel.jpqlbuilder.operators.IsEmpty;
import org.pavel.jpqlbuilder.operators.IsNotEmpty;
import org.pavel.jpqlbuilder.operators.Operator;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectionOperatorBuilderTest {
  @Test
  public void isEmpty() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new CollectionOperatorBuilder<>(chain, new ArrayList<>()).isEmpty().getOperator();

    assertTrue(operator instanceof IsEmpty);
    Assert.assertEquals("[] is empty", DummyJpqlStringWriter.asString(operator));
  }

  @Test
  public void isNotEmpty() {
    ExpressionChain chain = new ExpressionChain();
    Operator operator = new CollectionOperatorBuilder<>(chain, new ArrayList<>()).isNotEmpty().getOperator();

    assertTrue(operator instanceof IsNotEmpty);
    Assert.assertEquals("[] is not empty", DummyJpqlStringWriter.asString(operator));
  }
}
