package org.idey.algo.array;

/**
 * Created by i.dey on 4/6/17.
 */
public class MaxSumSubArray {
    public static void maxSumSubArray(int[] array){
        int max_so_far = Integer.MIN_VALUE, max_ending_here = 0,
                start =0, end = 0, s=0;

        for (int i=0; i< array.length; i++ )
        {
            max_ending_here += array[i];

            if (max_so_far < max_ending_here)
            {
                max_so_far = max_ending_here;
                start = s;
                end = i;
            }

            if (max_ending_here < 0)
            {
                max_ending_here = 0;
                s = i+1;
            }
        }

        System.out.println("Start Index "+start+" end index "+end+" total sum "+max_so_far);
    }

    public static void main(String[] args) {
        MaxSumSubArray.maxSumSubArray(new int[]{0,-1,-2,7});
    }

}
