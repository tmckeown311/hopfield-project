import java.io.File;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class discretehopfield {

    neuron[] neurons;

    public discretehopfield(int len, int numInputs){
        neurons = new neuron[len];
        for (int i = 0; i < len; i++){
            neurons[i] = new neuron(numInputs, i);
        }
    }

    //matrix functions
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

        int[][] result = new int[100][100];

        for (int curr = 0; curr < 10; curr++){
            for (int curc = 0; curc < 10; curc++){
                for (int i = 0; i < rows1; i++) {
                    for (int j = 0; j < cols2; j++) {
                        result[(curr*10)+i][(curc*10)+j] = matrix1[curr][curc] * matrix2[i][j];
                    }
                }
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

    //set neuron weights with reading weight file
    public void readWeightFile(File input){
        Scanner weightScan;
        int [][] weightMatrix = new int[100][100];
        try{
            weightScan = new Scanner(input);
            //jump past the headers of the file
            weightScan.nextLine();
            weightScan.nextLine();
            int[][] curMatrix = new int[10][10];
            while (weightScan.hasNext()) {
                
                //double for loop reads one matrix
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
                //add outer product to weight matrix sum
                int[][] transposeMatrix = transpose(curMatrix);
                int[][] productMatrix = outerProduct(transposeMatrix, curMatrix);
                weightMatrix = add(weightMatrix, productMatrix);

                weightScan.nextLine();
            }
            //set diagonal elements to 0
            for (int e = 0; e < 100; e++){
                weightMatrix[e][e] = 0;
            }
            //add weight vectors by column to neurons as their own weight vectors
            for (int c = 0; c< 100; c++){
                int[] weightVector = new int [10];
                for (int r = 0; r<100; r++){
                    weightVector[r] = weightMatrix[r][c];
                }
                neurons[c].setWeights(weightVector);
            }

        }

        catch(Exception e) {
            System.out.println(e);
        }

    }

    public boolean allElementsNotZero(ArrayList<Integer> arr){
        for (int i = 0; i < arr.size(); i++){
            if (arr.get(i) != 0){
                return true;
            }
        }
        return false;
    }

    public void testInput(File inputFile){
        Scanner inputScan;
        int[][] inputMatrix = new int[10][10];
        try{
            inputScan = new Scanner(inputFile);
            inputScan.nextLine();
            inputScan.nextLine();
            
            while (inputScan.hasNext()) {
                
                //double for loop reads one matrix
                for (int r = 0; r < 10; r++){
                    for (int c = 0; c<10; c++){
                        String curIn = inputScan.next();
                        if (curIn == "0"){
                            inputMatrix[r][c] = 1;
                        }
                        else{
                            inputMatrix[r][c] = -1;
                        }

                    }

                }
                Random random = new Random();
                //initialize list of remaining neurons to choose from while testing
                ArrayList<Integer> neuronsLeft= new ArrayList<>();
                for (int i = 0; i < 100; i++){
                    neuronsLeft.add(i);
                }
                int [][] y = Arrays.copyOf(inputMatrix, inputMatrix.length);
                while (allElementsNotZero(neuronsLeft)){
                    //randomly select neuron
                    int index = random.nextInt(neuronsLeft.size());
                    int nextIndex = neuronsLeft.get(index);
                    neuronsLeft.remove(index);
                    //idk how to get value xi for testing rn
                    neuron curNeuron = neurons[nextIndex];
                    int [] yin = new int[100];
                    for (int i = 0; i < 100; i++){
                        yin[i] = y[i][nextIndex];
                    }

                    int answer = curNeuron.calcAnswer(yin, );
                }

            }
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

}
