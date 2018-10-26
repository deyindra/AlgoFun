package org.idey.algo.iterator;

import org.idey.algo.util.AssertJ;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * Iterator class which which will iterate over number of elements and transform the element
 * after applying function
 * <pre>
 * Function&lt;Integer,Integer&gt; function = integer -&gt; integer+2;
 * Iterator&lt;Integer&gt; it = Arrays.asList(1,2,3).iterator();
 * Iterator&lt;Integer&gt; transformationIterator =
                    new TransformationIterator&lt;&gt;(it,function);
 *       while (transformationIterator.hasNext()){
 *          //print 3,4,5
 *          System.out.println(transformationIterator.next());
 *       }
 * </pre>
 */
public class TransformationIterator<I,R> implements Iterator<R> {
    private Iterator<I> iIterator;
    private Function<I,R> function;

    /**
     * @param iIterator Any {@link Iterator}
     * @param function {@link Function} which will transform the value
     * @throws IllegalArgumentException in case {@link Iterator} or {@link Function} is null
     */
    public TransformationIterator(Iterator<I> iIterator, Function<I, R> function) {
        AssertJ.notNull(function, "Function can not be null");
        AssertJ.notNull(iIterator, "Iterator can not be null");
        this.iIterator = iIterator;
        this.function = function;
    }

    /**
     *
     * @return true in case {@link Iterator#hasNext()} return true else false
     */
    @Override
    public boolean hasNext() {
        return iIterator.hasNext();
    }

    /**
     *
     * @return Value after transformation by applying {@link Function}
     * @throws NoSuchElementException in case there is no more elements
     */
    @Override
    public R next() {
        if(!hasNext()){
            throw new NoSuchElementException("No more elements");
        }
        return function.apply(iIterator.next());
    }

    /**
     * @throws UnsupportedOperationException as this is not supported
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported yet");
    }

}
