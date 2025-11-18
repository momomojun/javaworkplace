package com.example;

// Q10: Develop a program that calculates the sum of all elements of a matrix.
// Use loops. For example, in case of matrix {{1,2,3}, {4,5,6}}, the output
// should be 21 which is 1+2+3+4+5+6.
// Q11: Initiate any matrix of any size no less than 2 x 3 in your program. Show
// the matrix as an output of the program. Run the program. Do not use square
// matrices (with the same number of rows and columns).
class MatrixCalculate {
    // Q10: Develop a program that calculates the sum of all elements of a matrix.
    public static int sumOfMatrix(int[][] matrix) {
        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sum = sum + matrix[i][j];
            }
        }
        return sum;
    }

    // print the matrix
    public static void outputMatrix(int[][] matrix){
        for (int i = 0; i < matrix.length; i++ ){
            for (int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}

public class App {
    public static void main(String[] args) {
        // Q10: Develop a program that calculates the sum of all elements of a matrix.
        // Use loops. For example, in case of matrix {{1,2,3}, {4,5,6}}, the output
        // should be 21 which is 1+2+3+4+5+6.
        //MatrixCalculate matrixcalculate = new MatrixCalculate();
        int[][] testMatrix = {
                { 1, 2, 3 },
                { 4, 5, 6 }
        };
        System.out.println("The test matrix is:");
        MatrixCalculate.outputMatrix(testMatrix);
        System.out.println("the sum of all elements of the matrix: ");
        System.err.println(MatrixCalculate.sumOfMatrix(testMatrix));
        // Q11: Initiate any matrix of any size no less than 2 x 3 in your program. Show
        // the matrix as an output of the program. Run the program. Do not use square
        // matrices (with the same number of rows and columns).
        int[][] realMatrix = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 9 },
                { 10, 11, 12 },
                { 13, 14, 15 }
        };
        System.out.println("The matrix I created is:");
        MatrixCalculate.outputMatrix(realMatrix);
        System.out.println("the sum of all elements of the matrix: ");
        System.err.println(MatrixCalculate.sumOfMatrix(realMatrix));
        int x = 5;
        if(x < 5)
            x -= 1;
        x += 1;
        System.out.println(x);
    }
}
