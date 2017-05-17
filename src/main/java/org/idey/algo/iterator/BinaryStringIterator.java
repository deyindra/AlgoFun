package org.idey.algo.iterator;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryStringIterator implements Iterator<String> {
    private int currentIndex;
    private int bit;
    private int size;

    public BinaryStringIterator(int bit) {
        assert bit<=31;
        this.bit = bit;
        if(this.bit<31){
            size = 1 << bit;
        }else{
            size = Integer.MAX_VALUE;
        }
        currentIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < size;
    }

    @Override
    public String next() {
        if(!hasNext()){
            throw new NoSuchElementException();
        }
        StringBuilder builder = new StringBuilder("");
        for(int i=this.bit-1;i>=0; i--){
            if((currentIndex & (1<<i)) != 0 ){
                builder.append("1");
            }else{
                builder.append("0");
            }
        }
        currentIndex++;
        return builder.toString();
    }


    public static void main(String[] args) {
        BinaryStringIterator iterator = new BinaryStringIterator(3);
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
