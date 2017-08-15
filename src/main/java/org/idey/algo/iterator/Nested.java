package org.idey.algo.iterator;

import java.util.Iterator;

public class Nested<T> {
    private T object;
    private Iterator<Nested<T>> it;
    private boolean isObject;

    private Nested(T object, Iterator<Nested<T>> it, boolean isObject) {
        this.object = object;
        this.it = it;
        this.isObject = isObject;
    }

    public T getObject() {
        return object;
    }

    public Iterator<Nested<T>> getIt() {
        return it;
    }

    public boolean isObject() {
        return isObject;
    }

    public static class NestedBuilder<T>{
        public Nested<T> withObject(T object){
            return new Nested<T>(object,null,true);
        }
        public Nested<T> withIterator(Iterator<Nested<T>> it){
            return new Nested<>(null, it,false);
        }
    }
}
