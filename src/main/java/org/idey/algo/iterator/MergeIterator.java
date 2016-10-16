package org.idey.algo.iterator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * Iterator which will take multiple Iteratiors of sorted values and return final sorted output
 * <pre>
 *       Iterator&lt;Integer&gt; it1 = Arrays.asList(3,7,10).iterator();
 *       Iterator&lt;Integer&gt; it2 = Arrays.asList(1,4,8).iterator();
 *       MergeIterator&lt;Integer&gt; mergeIterator = new MergeIterator&lt;Integer&gt;(it1, it2);
 *       while(mergeIterator.hasNext()){
 *           //pring 1,3,4,7,8,10
 *           System.out.println(mergeIterator.next());
 *       }
 * </pre>
 */
public class MergeIterator<T extends Comparable<T>> implements Iterator<T> {
    /**
     * {@link PriorityQueue} to hold the {@link Iterator#next()} elements
     */
    private PriorityQueue<Node<T>> pq = new PriorityQueue<>();
    /**
     * List of {@link Iterator}
     */
    private List<Iterator<T>> list;

    /**
     *
     * @param iterators {@link Iterator} array
     */
    @SafeVarargs
    public MergeIterator(Iterator<T>... iterators) {
        if(iterators!=null && iterators.length>0){
            list = Arrays.asList(iterators);
        }else{
            list = Collections.emptyList();
        }
        int index=0;
        for(Iterator<T> it:list){
            if(it!=null && it.hasNext()){
                pq.offer(new Node<>(it.next(),index));
            }
            index++;
        }
    }

    /**
     *
     * @return true if {@link PriorityQueue#isEmpty()} return false else it will return false
     */
    @Override
    public boolean hasNext() {
        return !pq.isEmpty();
    }

    /**
     *
     * @return T element from Top of {@link PriorityQueue}
     * @throws NoSuchElementException in case of there is no elements in {@link PriorityQueue}
     */
    @Override
    public T next() {
        if(!hasNext()){
            throw new NoSuchElementException("No more elements");
        }
        Node<T> object = pq.poll();
        Iterator<T> it = list.get(object.index);
        if(it.hasNext()){
            pq.offer(new Node<>(it.next(),object.index));
        }
        return object.object;
    }

    /**
     * @throws UnsupportedOperationException as remove is not supported
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation not supported");
    }

    /**
     *
     * @param <T> Object implements {@link Comparable}
     */
    private static class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
        //T object implements Comparable
        private T object;
        //Index postions of Individual Iterator in Iterator array
        private int index;

        private Node(T object, int index) {
            this.object = object;
            this.index = index;
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public int compareTo(Node<T> o) {
            T thisObject = this.object;
            T anotherObject = o.object;
            if (thisObject == null) {
                return -1;
            } else {
                if(anotherObject == null){
                    return  1;
                }else{
                    return thisObject.compareTo(anotherObject);

                }
            }
        }
    }

}
