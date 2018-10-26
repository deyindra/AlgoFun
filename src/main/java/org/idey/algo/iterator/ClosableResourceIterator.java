package org.idey.algo.iterator;

import java.io.Closeable;
import java.util.Iterator;

/**
 * Iterator which used some resource needs to be closed after execution
 * <em>Any concrete {@link ClosableResourceIterator} class either must be initialized with
 * try with resource or should call {@link ClosableResourceIterator#close()} after complele
 * execution.</em>
 * @see Iterator
 * @see Closeable
 */
public interface ClosableResourceIterator<T>  extends Iterator<T>, Closeable{
    @Override
    default void remove() {
        throw new UnsupportedOperationException("Invalid Operation");
    }
}
