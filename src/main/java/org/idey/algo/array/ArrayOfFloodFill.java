package org.idey.algo.array;

import java.util.Arrays;
import java.util.function.Predicate;

public class ArrayOfFloodFill {

    private static <T> boolean isSafe(T[][] array, int rowIndex, int colIndex,Predicate<T> predicate){
        final int ROW = array.length;
        final int COL = array[0].length;
        return rowIndex>=0 && rowIndex<ROW && colIndex>=0 && colIndex<COL
                && predicate.test(array[rowIndex][colIndex]);
    }


    public static <T> void floodFill(T[][] array, int rowIndex, int colIndex, T newObject, Predicate<T> predicate){
        int rowNbr[] = new int[] {-1, -1, -1, 0, 0,  1, 1, 1};
        int colNbr[] = new int[] {-1,  0,  1, -1, 1, -1, 0, 1};

        if(!isSafe(array,rowIndex,colIndex,predicate)){
            return;
        }
        array[rowIndex][colIndex]=newObject;
        for(int i=0;i<8;i++){
            floodFill(array,rowIndex+rowNbr[i], colIndex+colNbr[i],newObject, predicate);
        }
    }


    public static void main(String[] args) {
        Integer M[][]=  new Integer[][] {
                {1, 1, 0, 0, 0},
                {0, 1, 0, 0, 1},
                {1, 0, 0, 1, 1},
                {0, 0, 0, 0, 1},
                {1, 0, 1, 0, 1}
        };
        floodFill(M,2,2,1,t-> t==0);
        for(Integer[] array:M){
            System.out.println(Arrays.deepToString(array));
        }

    }
}
