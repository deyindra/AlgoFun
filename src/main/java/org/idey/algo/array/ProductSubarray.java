package org.idey.algo.array;

public class ProductSubarray {

    public static int maxProductConstantSpace(int[] nums){
        int min_so_far = nums[0];
        int max_so_far = nums[0];
        int curr_max;
        int curr_min;
        int result = 0;
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] > 0){
                curr_max = Math.max(nums[i],nums[i] * max_so_far);
                curr_min = Math.min(nums[i] , nums[i] * min_so_far);

            } else {
                curr_max = Math.max(nums[i],nums[i] * min_so_far);
                curr_min = Math.min(nums[i] , nums[i] * max_so_far);
            }
            max_so_far = curr_max;
            min_so_far = curr_min;
            result = Math.max(result,max_so_far);
        }
        return result;
    }

    public static void main (String[] args) {

        int arr[] = {0,-1,-2,-3};
        System.out.println("Maximum Sub array product is "+
                maxProductConstantSpace(arr));
    }
}