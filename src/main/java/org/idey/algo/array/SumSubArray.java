package org.idey.algo.array;

public class SumSubArray {
    public static int maxSubArray(int[] array){
        int max_so_far = array[0];
        int curr_max = array[0];

        for (int i = 1; i < array.length; i++)
        {
            curr_max = Math.max(array[i], curr_max+array[i]);
            max_so_far = Math.max(max_so_far, curr_max);
        }
        return max_so_far;
    }

    public static void main(String[] args) {
        int arr[] = {-5,-1,-2,-4,-3,0};
        System.out.println("Maximum Sub array product is "+
                maxSubArray(arr));
    }

}
