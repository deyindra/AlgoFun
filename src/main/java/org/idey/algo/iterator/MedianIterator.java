package org.idey.algo.iterator;

import org.idey.algo.util.AssertJ;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.function.Predicate;

/**
 * {@link Iterator} which will return median from a sorted object
 * <pre>
 *     Iterator&lt;Integer&gt; it = new MedianIterator&lt;Integer&gt;(new Integer[]{1,4,3,2,6,11,9},3)
 *     while(it.hasNext()){
 *          //print 3,3,3,6,9
 *         System.out.println(it.next());
 *     }
 *
 * </pre>
 * @param <T> object which implements {@link Comparable}
 */
public class MedianIterator<T extends Comparable<T>> implements Iterator<T> {
    //Array of object T
    private T[] array;
    //Median T
    private T median;
    private int currentIndex;
    private final int size;
    //MinHeap
    private PriorityQueue<T> minHeap = new PriorityQueue<>();
    //MaxHeap
    private PriorityQueue<T> maxHeap = new PriorityQueue<>(Collections.reverseOrder());


    /**
     *
     * @param array Array of Objects implements {@link Comparable}
     * @param size window size
     * @throws  IllegalArgumentException in case array is null or empty and size is &lt;&#61;0 or size&gt;array.length
     */
    public MedianIterator(final T[] array, int size) {
        AssertJ.assertTrue(ts -> ts!=null && ts.length>0, array, "Invalid Array");
        AssertJ.assertTrue(i->i>0 && i<=array.length, size, "Invalid window size");
        this.array = array;
        this.size = size;
        maxHeap.offer(this.array[currentIndex]);
        currentIndex=1;
        for(;currentIndex<size;currentIndex++){
            add(array[currentIndex]);
        }
        currentIndex--;
        median = maxHeap.peek();
    }

    /**
     *
     * @return true provided currentIndex&lt;&#61;array.length-1
     */
    @Override
    public boolean hasNext() {
        return currentIndex<=array.length-1;
    }

    /**
     *
     * @return Object implements {@link Comparable}
     * @throws NoSuchElementException in case {@link MedianIterator#hasNext()} return false
     */
    @Override
    public T next() {
        if(!hasNext()){
            throw new NoSuchElementException("No more element");
        }
        T prevMedian = median;
        currentIndex++;
        if(currentIndex<=array.length-1){
            add(array[currentIndex]);
            remove(array[currentIndex-size]);
            median = maxHeap.peek();
        }
        return prevMedian;
    }

    private void add(T obj){
        T peek = maxHeap.peek();
        if(obj.compareTo(peek)>0){
            minHeap.offer(obj);
        }else{
            maxHeap.offer(obj);
        }
        balance();
    }

    private void remove(T obj){
        T peek = maxHeap.peek();
        if(obj.compareTo(peek)>0){
            minHeap.remove(obj);
        }else{
            maxHeap.remove(obj);
        }
        balance();
    }

    private void balance(){
        if(maxHeap.size()>minHeap.size()+1){
            minHeap.offer(maxHeap.poll());
        }else if(maxHeap.size()<minHeap.size()){
            maxHeap.offer(minHeap.poll());
        }
    }

    /**
     * @throws UnsupportedOperationException as this is not supoorted
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Invalid operations");
    }
}