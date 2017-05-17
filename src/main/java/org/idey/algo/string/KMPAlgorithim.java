package org.idey.algo.string;

import java.util.ArrayList;
import java.util.List;

public class KMPAlgorithim {
    public static <T> int[] processPattern(T[] array){
        int index  =0;
        int[] lps = new int[array.length];
        for(int i=1;i<array.length;){
            if(equals(array[i], array[index])){
                lps[i] = index + 1;
                i++;
                index++;
            }else{
                if(index!=0){
                    index = lps[index-1];
                }else{
                    lps[i]=0;
                    i++;
                }
            }
        }
        return lps;
    }

    public static <T> List<Integer> getPatternIndex(T[] array, T[] pattern){
        List<Integer> list = new ArrayList<>();
        int[] lps = processPattern(pattern);
        int i=0;
        int j=0;

        while (i<array.length && j<pattern.length){
            if(equals(array[i], pattern[j])){
                i++;
                j++;
            }else{
                if(j!=0){
                    j = lps[j-1];
                }else{
                    i++;
                }
            }
            if(j==pattern.length){
                list.add(i-pattern.length);
                j = lps[j-1];
            }

        }
        return list;
    }

    public static <T>  boolean equals(T a, T b){
        if(a==null)
            return (b==null);
        else
            return a.equals(b);
    }


    public static void main(String[] args) {
        System.out.println(getPatternIndex(new Integer[]{1,2,1,2,1}, new Integer[]{1,2,1}));
    }
}
