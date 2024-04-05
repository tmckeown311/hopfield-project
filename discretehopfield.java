import java.io.File;
import java.util.Scanner;
import java.util.random.*;

public class discretehopfield {

    neuron[] neurons;

    public discretehopfield(int len, int numInputs){
        neurons = new neuron[len];
        for (int i = 0; i < len; i++){
            neurons[i] = new neuron(numInputs, i);
        }
    }

    public static int[][] transpose(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] transposeMatrix = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposeMatrix[j][i] = matrix[i][j];
            }
        }

        return transposeMatrix;
    }

    public static int[][] outerProduct(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int rows2 = matrix2.length;
        int cols2 = matrix2[0].length;

        if (cols1 != rows2) {
            throw new IllegalArgumentException("The number of columns in the first matrix must be equal to the number of rows in the second matrix.");
        }

        int[][] result = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                result[i][j] = matrix1[i][j] * matrix2[i][j];
            }
        }

        return result;
    }

    public static int[][] add(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;

        // Check if both matrices have the same dimensions
        if (rows != matrix2.length || cols != matrix2[0].length) {
            throw new IllegalArgumentException("Matrices must have the same dimensions for addition.");
        }

        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        return result;
    }

    public void readWeightFile(File input){
        Scanner weightScan;
        int [][] weightMatrix = new int[10][10];
        try{
            weightScan = new Scanner(input);
            //jump past the headers of the file
            weightScan.nextLine();
            weightScan.nextLine();
            while (weightScan.hasNext()) {
                int[][] curMatrix = new int[10][10];
                for (int r = 0; r < 10; r++){
                    for (int c = 0; c<10; c++){
                        String curIn = weightScan.next();
                        if (curIn == "0"){
                            curMatrix[r][c] = 1;
                        }
                        else{
                            curMatrix[r][c] = -1;
                        }

                    }

                }
                int[][] transposeMatrix = transpose(curMatrix);
                int[][] productMatrix = outerProduct(transposeMatrix, curMatrix);
                weightMatrix = add(weightMatrix, productMatrix);
                weightScan.nextLine();
            }

        }
        catch(Exception e) {
            System.out.println(e);
        }

    }

}
