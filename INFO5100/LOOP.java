//package INFO5100;
//import java.io.*;

public class LOOP {
    //how to distinguish {1,2,3}{1,2,3} or {1,2}{3,4}{5,6}
    //how to input a matrix
    //do we need to check whether the array is a matrix?
    //no method include method, or use another class
    //A row == B col
    //need float?
    public static int checkmatrices(int[][] matrixA, int[][] matrixB){
        int lengthA = matrixA[0].length;
        int lengthB = matrixB.length;
        if(lengthA == lengthB){
            System.out.println("It is a real matrices,go on!");
            return 1;
        }
        else{
            System.out.println("It is not a real matrices, please try again.");
            return -1;
        }
    }
    //mutiply,depend A row and B col to create new matrix
    public static int[][] mutiplematrices(int[][] matrixA, int[][] matrixB){
        int i, j, k;
        int lengthA, lengthB, lengthC;
        lengthA = matrixA.length; //A row
        lengthB = matrixA[0].length; //A col
        lengthC = matrixB[0].length; //B col
        int[][] sum = new int[lengthA][lengthC];

        for (i = 0; i < lengthA; i++) {
            for (j = 0; j < lengthC; j++) {
                for (k = 0; k < lengthB; k++) {
                    sum[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return sum;
    }
    //output
    public static void outputmatrices(int[][] outputmatrix){
        int lengthA = outputmatrix.length;
        int lengthB = outputmatrix[0].length;
        int i ,j;

        for(i = 0; i < lengthA; i++){
            for(j = 0; j< lengthB; j++){
                //print will not change to next line，println will
                System.out.print(outputmatrix[i][j] + " ");
            }
            //use println to output empty, no "\n"
            System.out.println();
        }
    }
    public static void main(String[] args){
        int[][] matrixA = {
            {2,3,4},
            {3,4,5}
        };
        int [][] matrixB = {
            {1,2},
            {3,4},
            {5,6}
        };
        if(checkmatrices(matrixA, matrixB) == 1){
            int[][] sum = mutiplematrices(matrixA, matrixB);
            outputmatrices(sum);
        }
    }
}
