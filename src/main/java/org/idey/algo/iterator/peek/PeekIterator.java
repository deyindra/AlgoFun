package org.idey.algo.iterator.peek;

import java.util.Iterator;

/**
 * Special Iterator which will provide addiotnal method {@link PeekIterator#peek()}
 * @param <T> return the current object
 *
 */
public interface PeekIterator<T> extends Iterator<T> {
    /**
     * This method always current element from the iterator. During the process of peeking element it will not
     * advance the iterator
     * @return T current object
     * @throws java.util.NoSuchElementException in case there is no more elements or the iterator is null
     */
    T peek();

    /**
     * @throws UnsupportedOperationException as this is not supported
     */
    default void remove() {
        throw new UnsupportedOperationException("Invalid Operations");
    }
}
