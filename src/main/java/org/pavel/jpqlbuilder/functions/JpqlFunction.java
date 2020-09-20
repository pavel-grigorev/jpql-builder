package org.pavel.jpqlbuilder.functions;

import org.pavel.jpqlbuilder.operators.Operator;

/**
 * This is an abstract class not an interface to avoid clashing with functional interfaces in the method signatures.
 * @param <T> Return type of the function.
 */
public abstract class JpqlFunction<T> implements Operator {
}
