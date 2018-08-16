package org.idey.algo.array;

public class SecondLargestNumber {
    public static int secondLargest(int[] array){
        int firstMax = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;
        for(int x:array){
            if(x>firstMax){
                secondMax = firstMax;
                firstMax = x;
            }else if(x>secondMax && x<firstMax){
                secondMax = x;
            }
        }
        return secondMax;
    }

    public static void main(String[] args) {
        System.out.println(secondLargest(new int[]{5,4,3,8,9,10, 11,11}));
    }
}
