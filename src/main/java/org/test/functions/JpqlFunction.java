package org.test.functions;

import org.test.operators.Operator;

/**
 * This is an abstract class not an interface to avoid clashing with functional interfaces in the method signatures.
 * @param <T> Type of the value the function supports.
 */
public abstract class JpqlFunction<T> implements Operator {
}
