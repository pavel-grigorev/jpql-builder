package org.test.functions;

import org.test.operators.Operator;

/**
 * This is an abstract class not an interface to avoid clashing with functional interfaces in the method signatures.
 * @param <T> Return type of the function.
 */
public abstract class JpqlFunction<T> implements Operator {
}
