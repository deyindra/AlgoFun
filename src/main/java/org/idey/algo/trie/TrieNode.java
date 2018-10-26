package org.idey.algo.trie;

import java.util.Optional;

public class TrieNode<T> {
    private boolean isEnd;
    private Optional<T> value;
    private TrieNode[] array;

    public TrieNode() {
        array = new TrieNode[128];
        isEnd = false;
        value = Optional.empty();
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd() {
        isEnd = true;
    }

    public TrieNode<T>[] getArray() {
        return array;
    }

    public T getValue() {
        return value.get();
    }

    public void setValue(T value) {
        this.value = Optional.of(value);
    }

    public boolean containsKey(char ch){
        return array[ch]!=null;
    }

    public TrieNode<T> get(char ch){
        return array[ch];
    }

    public void put(char ch, TrieNode<T> node){
        this.array[ch] = node;
    }

}
