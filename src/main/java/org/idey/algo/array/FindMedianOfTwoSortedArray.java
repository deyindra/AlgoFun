package org.idey.algo.array;

public class FindMedianOfTwoSortedArray {
    //O(log(min(x,y))
    public static double findMedian(int[] input1, int[] input2){
        if(input1.length>input2.length){
            return findMedian(input2,input1);
        }
        int x = input1.length;
        int y = input2.length;

        int low = 0;
        int high = x;

        while (low <= high){
            int partitionX = (low + high)/2;
            int partitionY = (x+y+1)/2 - partitionX;

            int maxLeftX = (partitionX==0) ? Integer.MIN_VALUE : input1[partitionX-1];
            int minRightX = (partitionX == x) ? Integer.MAX_VALUE : input1[partitionX];

            int maxLeftY = (partitionY==0) ? Integer.MIN_VALUE : input2[partitionY-1];
            int minRightY = (partitionY == y) ? Integer.MAX_VALUE : input2[partitionY];

            if(maxLeftX<=minRightY && maxLeftY<=minRightX){
                if((x+y)%2==0){
                    return (double)(Math.max(maxLeftX,maxLeftY) + Math.min(minRightX,minRightY))/2;
                }else{
                    return (double)(Math.max(maxLeftX,maxLeftY));
                }
            }else if(maxLeftX>minRightY){
                high = partitionX -1;
            }else{
                low = partitionX + 1;
            }
        }
        throw new IllegalArgumentException();
    }

    public static <T extends Comparable<T>> int findPivote(T[] array){
        int low = 0;
        int high = array.length - 1;
        while (array[low].compareTo(array[high])>0){
            int mid = low + (high - low)/2;
            if(array[mid].compareTo(array[high]) > 0 ){
                low = mid + 1;
            }else{
                high = mid;
            }
        }
        return low;
    }

    public static <T extends Comparable<T>> int findPeak(T[] array, int low, int high){
        int mid = low + (high - low)/2;

        if((mid==0 || array[mid].compareTo(array[mid-1]) >=0) &&
                (mid==array.length-1 || array[mid].compareTo(array[mid+1]) >=0)){
            return mid;
        }


        if(mid>0 && array[mid-1].compareTo(array[mid])>0){
            return findPeak(array,low,mid-1);
        }else {
            return findPeak(array, mid + 1, high);
        }
    }

    public static void main(String[] args) {
        System.out.println(findMedian(new int[]{1,5,6,7},new int[]{3,4}));
        System.out.println(findPivote(new Integer[]{3,4,1,2}));
        System.out.println(findPeak(new Integer[]{1,2,3,4,3,2,1},0, 6));
    }

}
