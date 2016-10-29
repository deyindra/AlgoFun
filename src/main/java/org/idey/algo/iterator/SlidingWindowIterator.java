package org.idey.algo.iterator;

import org.idey.algo.util.AssertJ;

import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Sliding/Rolling Window Iterator which return group of object from given {@link Iterator}
 * <pre>
 *     Iterator&lt;Integer&gt; it = Arrays.asList(1,2,3,4,5,6).iterator();
 *     SlidingWindowIterator&lt;Integer&gt; slidingWindow = new SlidingWindowIterator&lt;&gt;(it, 2, 3);
 *     while(slidingWindow.hasNext()){
 *         //print [1,2] [4,5]
 *         System.out.println(slidingWindow.next());
 *     }
 * </pre>
 * @param <T> T object
 */
public class SlidingWindowIterator<T> implements Iterator<List<T>>{
    //Underlying Iterator
    private Iterator<T> it;
    //Size of the window
    private final int size;
    //Steps from from where window start
    private final int step;
    private final Deque<T> deque;
    private boolean hasNext;

    /**
     *
     * @param it {@link Iterator}
     * @param size size of the window
     * @param step step form where window will start
     * @throws IllegalArgumentException in case of {@link Iterator} is null
     * @throws IllegalArgumentException in case size &lt;0
     * @throws IllegalArgumentException in case of step &lt;0
     */
    public SlidingWindowIterator(Iterator<T> it, int size, int step) {
        AssertJ.notNull(it, "Iterator can not be null");
        AssertJ.assertTrue(i->i>0, size, "size can not be zero or negative");
        AssertJ.assertTrue(i->i>0, step, "step can not be zero or negative");
        this.it = it;
        this.size = size;
        this.step = step;
        deque = new LinkedList<>();
        setAdvance();
    }

    /**
     *
     * @return true in case hasNext will return true
     */
    @Override
    public boolean hasNext() {
        return hasNext;
    }

    /**
     *
     * @return {@link Collections#unmodifiableList(List)} of given size
     * @throws NoSuchElementException in case there is no more elements
     */
    @Override
    public List<T> next() {
        if(!hasNext){
            throw new NoSuchElementException();
        }
        List<T> list = Collections.unmodifiableList(new LinkedList<>(this.deque));
        setAdvance();
        return list;
    }

    /**
     * @throws UnsupportedOperationException as this operation is not supported
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Invalid Operation");
    }

    private void setAdvance(){
        hasNext=false;
        //Check first if the iterator is empty of not
        if(it.hasNext()){
            //if deque is not empty
            if(!deque.isEmpty()){
                //window size is greater than step
                if(size>step){
                    //remove all the element from the begining
                    // based on given step and if deque is not empty
                    for(int count=0;!deque.isEmpty() && count<step; count++){
                        deque.poll();
                    }
                //if size less than or equal to step
                }else if(size<=step){
                    //clear dqueue
                    deque.clear();
                    //Move the pointer in the iterator based on difference of step and size
                    for(int count=0;it.hasNext() && count<step-size; count++){
                        it.next();
                    }
                }
            }
            //finally populate deque
            while (it.hasNext() && deque.size()<size){
                deque.offer(it.next());
            }
            if(!deque.isEmpty()) {
                hasNext = true;
            }
        }
    }

}
