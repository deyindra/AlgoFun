package org.idey.algo.iterator;

import java.util.*;

public class NestedIterator<T> implements Iterator<T> {
    private T current;
    private Stack<Iterator<Nested<T>>> stack;
    private boolean hasNext;

    public NestedIterator(Iterator<Nested<T>> it) {
        stack = new Stack<>();
        stack.push(it);
        hasNext=false;
        setAdvance();
    }

    private void setAdvance() {
        hasNext = false;
        while (!stack.isEmpty()){
            Iterator<Nested<T>> it = stack.peek();
            if(it==null || !it.hasNext()){
                stack.pop();
            }else{
                Nested<T> nested = it.next();
                if(nested.isObject()){
                    current = nested.getObject();
                    hasNext = true;
                    return;
                }else{
                    stack.push(nested.getIt());
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public T next() {
        if(!hasNext()){
            throw new NoSuchElementException();
        }
        T result = current;
        setAdvance();
        return result;
    }

    public static void main(String[] args) {
        List<Nested<Integer>> nestedList1 = new ArrayList<>();
        nestedList1.add(new Nested.NestedBuilder<Integer>().withObject(1));
        nestedList1.add(new Nested.NestedBuilder<Integer>().withIterator(Arrays.asList(
                new Nested.NestedBuilder<Integer>().withObject(5),
                new Nested.NestedBuilder<Integer>().withObject(6),
                new Nested.NestedBuilder<Integer>().
                        withIterator(Collections.singleton(new Nested.NestedBuilder<Integer>().withObject(7)).iterator())
                ).iterator()));


        List<Nested<Integer>> nestedList2 = new ArrayList<>();
        nestedList2.add(new Nested.NestedBuilder<Integer>().withObject(2));
        nestedList2.add(new Nested.NestedBuilder<Integer>().withIterator(Arrays.asList(
                new Nested.NestedBuilder<Integer>().withObject(3),
                new Nested.NestedBuilder<Integer>().withObject(4)).iterator()));

        MergeIterator<Integer> mergeIterator = new MergeIterator<>(new NestedIterator<>(nestedList1.iterator()), new NestedIterator<>(nestedList2.iterator()));
        while (mergeIterator.hasNext()){
            System.out.println(mergeIterator.next());
        }
    }


}
