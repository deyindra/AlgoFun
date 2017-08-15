package org.idey.algo.array;

public class SubArraySumProduct {


    private  interface SubArray{
        int calculate(int[] array);
    }


    public static class SumSubArray implements SubArray{
        @Override
        public int calculate(int[] array) {
            int max_so_far = array[0];
            int curr_max = array[0];

            for (int i = 1; i < array.length; i++)
            {
                curr_max = Math.max(array[i], curr_max+array[i]);
                max_so_far = Math.max(max_so_far, curr_max);
            }
            return max_so_far;
        }
    }

    public static class ProductSubArray implements SubArray{
        @Override
        public int calculate(int[] array) {
            int min_so_far = array[0];
            int max_so_far = array[0];
            int curr_max;
            int curr_min;
            int result = 0;
            for (int i = 1; i < array.length; i++) {
                if(array[i] > 0){
                    curr_max = Math.max(array[i],array[i] * max_so_far);
                    curr_min = Math.min(array[i] , array[i] * min_so_far);

                } else {
                    curr_max = Math.max(array[i],array[i] * min_so_far);
                    curr_min = Math.min(array[i] , array[i] * max_so_far);
                }
                max_so_far = curr_max;
                min_so_far = curr_min;
                result = Math.max(result,max_so_far);
            }
            return result;
        }
    }

    public static void main(String[] args) {
        int arr[] = {0,-1,-2,7,-5};
        System.out.println(new ProductSubArray().calculate(arr));
    }

}
