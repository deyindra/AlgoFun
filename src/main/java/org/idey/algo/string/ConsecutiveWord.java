package org.idey.algo.string;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ConsecutiveWord {
    public static class MaxCharCount{
        private char ch;
        private int count;

        public MaxCharCount(char ch, int count) {
            this.ch = ch;
            this.count = count;
        }

        @Override
        public String toString() {
            return "MaxCharCount{" +
                    "ch=" + ch +
                    ", count=" + count +
                    '}';
        }
    }
    public static MaxCharCount getMaxConsecutiveCharCount(String str){
        char[] array = str.toCharArray();
        int count=1;
        char prevChar = array[0];
        int maxCount=count;
        char maxChar=prevChar;
        for(int i=1;i<array.length;i++){
            if(prevChar==array[i]){
                count++;
            }
            if(prevChar!=array[i] || i==array.length-1){
                if(maxCount<count){
                    maxCount = count;
                    maxChar = prevChar;
                }
                count=1;
                prevChar = array[i];
            }
        }
        return new MaxCharCount(maxChar, maxCount);
    }

    public static void main(String[] args) {
        System.out.println(getMaxConsecutiveCharCount("AABBBAAAAACC"));
        System.out.println(getTopNCharCount("AAAAABBBAAAAAD",false,2));

    }


    public static class CharCount{
        private char ch;
        private int count;
        private int startIndex;

        public CharCount(char ch,int startIndex, int count) {
            this.ch = ch;
            this.count = count;
            this.startIndex = startIndex;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CharCount charCount = (CharCount) o;

            if (ch != charCount.ch) return false;
            return startIndex == charCount.startIndex;
        }

        @Override
        public int hashCode() {
            int result = (int) ch;
            result = 31 * result + startIndex;
            return result;
        }

        @Override
        public String toString() {
            return "CharCount{" +
                    "ch=" + ch +
                    ", count=" + count +
                    ", startIndex=" + startIndex +
                    '}';
        }
    }


    public static PriorityQueue<CharCount> getTopNCharCount(String str, boolean isMax, int limit){
        PriorityQueue<CharCount> priorityQueue = new PriorityQueue<>(new Comparator<CharCount>() {
            @Override
            public int compare(CharCount o1, CharCount o2) {
                return isMax ? (o1.count - o2.count) : -(o1.count - o2.count);
            }
        });
        char[] array = str.toCharArray();
        int startIndex = 0;
        int count=1;
        char prevChar = array[0];
        //just one char
        if(array.length==1){
            priorityQueue.offer(new CharCount(prevChar,startIndex,count));
        }else{
            for(int i=1;i<array.length;i++){
                if(prevChar==array[i]){
                    count++;
                }
                if(prevChar!=array[i]){
                    addToPQ(priorityQueue,limit,isMax,new CharCount(prevChar,startIndex,count));
                    count=1;
                    prevChar = array[i];
                    startIndex = i;
                }

                if(i==array.length-1){
                    addToPQ(priorityQueue,limit,isMax,new CharCount(prevChar,startIndex,count));
                }
            }
        }
        return priorityQueue;

    }




    private static void  addToPQ(PriorityQueue<CharCount> pq, int limit, boolean isMax, CharCount charCount){
        if(pq.contains(charCount)){
            pq.remove(charCount);
            pq.add(charCount);
        }else{
            int size = pq.size();
            if(size<limit){
                pq.offer(charCount);
            }else{
                CharCount head = pq.peek();
                if(isMax?(charCount.count > head.count) : (charCount.count<head.count)){
                    pq.poll();
                    pq.offer(charCount);
                }
            }
        }
    }



}
