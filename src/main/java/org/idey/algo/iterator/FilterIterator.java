package org.idey.algo.iterator;

import org.idey.algo.util.AssertJ;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * Iterator class which filters based on Certain Criteria
 * <pre>
 *     Iterator&lt;Integer&gt; it = Arrays.asList(1,2,3,4).iterator();
 *     Predicate&lt;Integer&gt; evenPredicate = new Predicate&lt;Integer&gt;(){
 *       public boolean test(Integer i){
 *           return i%2==0;
 *       }
 *     };
 *     Iterator&lt;Integer&gt; filterIterator = new FilterIterator&lt;Integer&gt;(evenPredicate, it);
 *     //print 2 and 4
 *     while(filterIterator.hasNext()){
 *         System.out.println(filterIterator.next());
 *     }
 *
 * </pre>
 * @see Iterator
 */
public class FilterIterator<T> implements Iterator<T>{
    /**
     * {@link Predicate} for statisfing the condition
     */
    private Predicate<T> predicate;
    //object which will satisfy the Predicate
    private T object;
    //return true if FilterIterator has elements
    private boolean hasNext;
    //Internal Iterator
    private Iterator<T> iterator;

    /**
     *
     * @param predicate {@link Predicate}
     * @param iterator {@link Iterator}
     * @throws IllegalArgumentException in case of {@link Predicate} is null
     */
    public FilterIterator(final Predicate<T> predicate,
                          final Iterator<T> iterator) {
        AssertJ.notNull(predicate, "Filter condition can not be null");
        this.predicate = predicate;
        this.iterator = iterator;
        setAdvance();
    }
    //advance the internal iterator to the element which satisfy the predicate
    private void setAdvance() {
        hasNext = false;
        if(this.iterator!=null){
            while (iterator.hasNext()){
                T object = iterator.next();
                if(predicate.test(object)){
                    this.object = object;
                    hasNext = true;
                    break;
                }
            }
        }
    }

    /**
     *
     * @return true if FilterIterator has elements to traverse or else return false
     */
    @Override
    public boolean hasNext() {
        return hasNext;
    }

    /**
     *
     * @return T element which satisfy the {@link Predicate}
     * @throws NoSuchElementException in case of there is no elements
     */
    @Override
    public T next() {
        if(!hasNext)
            throw new NoSuchElementException("End of Iterator");

        T prevObject = object;
        setAdvance();
        return prevObject;
    }

    /**
     * @throws UnsupportedOperationException as remove is not supported
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported");
    }
}
