package org.idey.algo.array;

import java.util.function.Predicate;

public class ArrayOfBigClustering {

     public static <T> boolean isSafe(T[][] array, boolean[][] visted,
                                     int rowIndex,
                                     int colIndex,Predicate<T> predicate){
        final int ROW = array.length;
        final int COL = array[0].length;

        return rowIndex>=0 && rowIndex<ROW && colIndex>=0 && colIndex<COL &&
                predicate.test(array[rowIndex][colIndex]) && !visted[rowIndex][colIndex];

    }

    public static <T> int dfs(T[][] array, boolean[][] visted,
                               int rowIndex,
                               int colIndex,Predicate<T> predicate, int numberOfItemPerCluster){


        int[] relativeRowIndex = {-1, -1, -1, 0,  0,  1, 1, 1};
        int[] relativeColIndex = {-1,  0,  1, -1, 1, -1, 0, 1};
        visted[rowIndex][colIndex]=true;
        for(int i=0;i<8;i++){
            if(isSafe(array,visted,rowIndex+relativeRowIndex[i],
                    colIndex+relativeColIndex[i],predicate)){
                numberOfItemPerCluster++;
                numberOfItemPerCluster = dfs(array,visted,rowIndex+relativeRowIndex[i],
                        colIndex+relativeColIndex[i],predicate, numberOfItemPerCluster);
            }
        }
        return numberOfItemPerCluster;
    }





    public static <T> int findCluster(T[][] array, Predicate<T> predicate, int numberOfItemPerCluster){
        final int ROW = array.length;
        final int COL = array[0].length;
        boolean[][] visted = new boolean[ROW][COL];
        int count = 0;
        for(int i=0;i<ROW;i++){
            for(int j=0;j<COL;j++){
                if(predicate.test(array[i][j]) && !visted[i][j]){
                    numberOfItemPerCluster++;
                    numberOfItemPerCluster = dfs(array,visted,i,j,predicate, numberOfItemPerCluster);
                    if(count<=numberOfItemPerCluster){
                        count = numberOfItemPerCluster;
                    }
                }else{
                    numberOfItemPerCluster=0;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Integer M[][]=  new Integer[][] {
                {1, 1, 0, 1, 0},
                {0, 1, 0, 0, 1},
                {1, 0, 0, 1, 1},
                {0, 0, 0, 1, 0},
                {1, 0, 1, 0, 1}
        };

        System.out.println(findCluster(M, t -> t==1, 0));
    }

}
