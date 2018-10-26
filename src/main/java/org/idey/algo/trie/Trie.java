package org.idey.algo.trie;

import java.util.LinkedHashMap;
import java.util.Map;


public class Trie<T> {
    private TrieNode<T> root;

    public Trie() {
        root = new TrieNode<>();
    }

    public void insert(String word, T value){
        TrieNode<T> node = root;
        for(char ch:word.toCharArray()){
            if(!node.containsKey(ch)){
                node.put(ch, new TrieNode<>());
            }
            node = node.get(ch);
        }
        node.setEnd();
        node.setValue(value);
    }


    private TrieNode<T> searchPrefix(String word){
        TrieNode<T> node = root;
        for(char ch:word.toCharArray()){
            node = node.get(ch);
            if(node==null){
                return null;
            }
        }
        return node;
    }

    public Map<String, T> word(String prefix){
        Map<String, T> map = new LinkedHashMap<>();
        TrieNode<T> node = searchPrefix(prefix);
        if(node!=null){
            populateMap(node,map,prefix);
        }
        return map;
    }

    public Map<String, T> word(){
        Map<String, T> map = new LinkedHashMap<>();
        populateMap(root,map,"");
        return map;
    }

    public T searchWord(String word){
        TrieNode<T> node = searchPrefix(word);
        if(node!=null && node.isEnd()){
            return node.getValue();
        }else{
            return null;
        }
    }

    private void populateMap(TrieNode<T> node, Map<String, T> map,String prefix){
        if(node.isEnd()){
            map.put(prefix,node.getValue());
        }
        TrieNode<T>[] array = node.getArray();

        for(int i=0;i<array.length;i++){
            if(array[i]!=null){
                populateMap(array[i],map,prefix+(char)i);
            }

        }
    }

    public static void main(String[] args) {
        Trie<String> trie = new Trie<>();
        trie.insert("John", "666-191-222");
        trie.insert("Jones","555-666-789");
        trie.insert("Joe","123-456-789");
        trie.insert("Josh","555-555-1345");
        trie.insert("Joshua","555-555-1345");
        trie.insert("Bob","123-456-7899");

        System.out.println(trie.word("JA"));
        System.out.println(trie.word("Jo"));
        System.out.println(trie.word());
        System.out.println(trie.word("Josh"));
        System.out.println(trie.searchWord("Jo"));
    }
}
