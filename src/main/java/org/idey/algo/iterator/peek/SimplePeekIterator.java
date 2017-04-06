package org.idey.algo.iterator.peek;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Special Iterator which will provide addiotnal method {@link SimplePeekIterator#peek()}
 * @param <T> element of the iterator
 * @see PeekIterator
 */
public class SimplePeekIterator<T> implements PeekIterator<T> {
    private Iterator<T> iterator;
    private T element;

    /**
     * @param iterator underlying iterator
     */
    public SimplePeekIterator(Iterator<T> iterator) {
        this.iterator = iterator;
        if (iterator!=null && iterator.hasNext()) {
            element = iterator.next();
        }
    }

    /**
     * this method will not advance to next element
     * @return T current element
     * @throws NoSuchElementException in case hasNext return false
     */
    @Override
    public T peek() {
        if(!hasNext()){
            throw new NoSuchElementException("No more element");
        }
        return element;
    }

    /**
     *
     * @return false whem element is null
     */
    @Override
    public boolean hasNext() {
        return element != null;
    }

    /**
     * @return T next element
     * @throws NoSuchElementException in case hasNext return false
     */
    @Override
    public T next() {
        if(!hasNext()){
            throw new NoSuchElementException("No more element");
        }
        T ret = element;
        element = this.iterator.hasNext() ? iterator.next() : null;
        return ret;
    }
}
