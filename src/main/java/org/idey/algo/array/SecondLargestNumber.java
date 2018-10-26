package org.idey.algo.array;


public class SecondLargestNumber {
    public static Integer secondLargest(int[] array){
        int firstMax = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;
        int thirdMax = Integer.MIN_VALUE;
        for(int i=0;i<array.length;i++){
            if(array[i]>firstMax){
                thirdMax = secondMax;
                secondMax = firstMax;
                firstMax = array[i];
            }else if(array[i]>secondMax && array[i]<firstMax){
                thirdMax = secondMax;
                secondMax = array[i];
            }else if(array[i]>thirdMax && array[i]<secondMax){
                thirdMax = array[i];
            }
        }
        return thirdMax;
    }

    public static void main(String[] args) {
        System.out.println(secondLargest(new int[]{5,6, 1,5,5,5,5,5}));
    }
}
