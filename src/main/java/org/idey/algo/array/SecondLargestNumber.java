package org.idey.algo.array;

public class SecondLargestNumber {
    public static Integer secondLargest(int[] array){
        int firstMax = array[0];
        Integer secondMax = null;
        Integer thirdMax = null;
        for(int i=1;i<array.length;i++){
            if(array[i]>firstMax){
                thirdMax = secondMax;
                secondMax = firstMax;
                firstMax = array[i];
            }else if(secondMax!=null && array[i]>secondMax && array[i]<firstMax){
                thirdMax = secondMax;
                secondMax = array[i];
            }else if(thirdMax!=null && array[i]>thirdMax && array[i]<secondMax){
                thirdMax = array[i];
            }
        }
        return thirdMax;
    }

    public static void main(String[] args) {
        System.out.println(secondLargest(new int[]{5,6, 5,5,5,5,5,5}));
    }
}
