package org.idey.algo.iterator;


import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Iterator which returns result based various combination
 * <pre>
 *     Iterator&lt;Integer&gt; it = Arrays.asList(1,2,3,4).iterator();
 *     PowersetIterator&lt;Integer&gt; powerSet = new PowersetIterator&lt;Integer&gt;(it,3,4);
 *     while(powerSet.hasNext()){
 *         //print [1,2,3],[2,3,4],[1,3,4],[1,2,4],[1,2,3,4]
 *         System.out.println(powerSet.next());
 *     }
 *
 * </pre>
 *
 */
public class PowersetIterator<E> implements Iterator<Set<E>> {
    //minimum Size of the powerSets
    private int minSize;
    //maximum Size of the powerSets
    private int maxSize;
    //Array to hold the elements
    private E[] arr = null;
    /**
     * {@link BitSet} for truning bit on or off for selecting items
     */
    private BitSet bset = null;

    /**
     *
     * @param set {@link Set} of Items
     * @param minSize minimum size of {@link PowersetIterator}
     * @param maxSize maximum size of {@link PowersetIterator}
     * @throws IllegalArgumentException in case {@link Set} is null
     */
    public PowersetIterator(final Set<E> set, final int minSize, final int maxSize) {
        if(set == null){
            throw new IllegalArgumentException("Invalid Sets");
        }
        initialize(set,minSize,maxSize);
    }

    /**
     * @param set {@link Set} of Items
     * @throws IllegalArgumentException in case {@link Set} is null
     */
    public PowersetIterator(Set<E> set){
        if(set == null){
            throw new IllegalArgumentException("Invalid Sets");
        }
        initialize(set,0,set.size());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final Set<E> set, final int minSize, final int maxSize){
        this.minSize = Math.min(minSize, set.size());
        this.maxSize = Math.min(maxSize, set.size());
        arr = (E[]) set.toArray();
        bset = new BitSet(arr.length + 1);
        if(set.isEmpty()){
            bset.set(0);
        }else {
            for (int i = 0; i < minSize; i++) {
                bset.set(i);
            }
        }
    }



    /**
     *
     * @return true if the last element in the {@link BitSet} is set to 0
     */
    @Override
    public boolean hasNext() {
        return !bset.get(arr.length);
    }

    /**
     *
     * @return Each {@link Set} of combination
     * @throws NoSuchElementException in case there are no more combination
     */
    @Override
    public Set<E> next() {
        if(!hasNext()){
            throw new NoSuchElementException("No more elements");
        }
        Set<E> returnSet = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            if (bset.get(i)) {
                returnSet.add(arr[i]);
            }
        }

        int count;
        do {
            incrementBitSet();
            count = countBitSet();
        } while ((count < minSize) || (count > maxSize));
        return returnSet;
    }

    /**
     * @throws UnsupportedOperationException as remove is not supported
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Invalid Operation");
    }


    private void incrementBitSet() {
        for (int i = 0; i < bset.size(); i++) {
            if (!bset.get(i)) {
                bset.set(i);
                break;
            } else
                bset.clear(i);
        }
    }

    private int countBitSet() {
        int count = 0;
        for (int i = 0; i < bset.size(); i++) {
            if (bset.get(i)) {
                count++;
            }
        }
        return count;

    }


}
