import java.io.File;
import java.io.FileWriter;
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
        int dimensions = rows1 * cols1;
        int N = cols1;

        if (cols1 != rows2) {
            throw new IllegalArgumentException("The number of columns in the first matrix must be equal to the number of rows in the second matrix.");
        }

        int[][] result = new int[dimensions][dimensions];

        for (int curr = 0; curr < rows1; curr++){
            for (int curc = 0; curc < cols1; curc++){
                for (int i = 0; i < rows2; i++) {
                    for (int j = 0; j < cols2; j++) {
                        result[(curr*N)+curc][(i*N)+j] = matrix1[curr][curc] * matrix2[i][j];
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
    public void readWeightFile(File input, File output){
        Scanner weightScan;
        FileWriter outputScan;
        try{
            weightScan = new Scanner(input);
            int dimensions =  weightScan.nextInt();
            weightScan.useDelimiter("");
            //jump past the headers of the file
            int N = (int) Math.sqrt(dimensions);
            int [][] weightMatrix = new int[dimensions][dimensions];
            weightScan.nextLine();
            int numImages = weightScan.nextInt();
            weightScan.nextLine();
            weightScan.nextLine();
            int[][] curMatrix = new int[N][N];
            for (int y = 0; y < numImages; y++) {
                
                //double for loop reads one matrix
                for (int r = 0; r < N; r++){
                    for (int c = 0; c<N; c++){
                        String curIn = weightScan.next();
                        if ("O".equals(curIn)){
                            curMatrix[r][c] = 1;
                        }
                        else{
                            curMatrix[r][c] = -1;
                        }

                    }
                    if (weightScan.hasNext()){
                        weightScan.nextLine();
                    }

                }
                //add outer product to weight matrix sum
                int[][] transposeMatrix = transpose(curMatrix);
                int[][] productMatrix = outerProduct(transposeMatrix, curMatrix);
                weightMatrix = add(weightMatrix, productMatrix);

                if (weightScan.hasNext()){
                    weightScan.nextLine();
                }
            }
            //set diagonal elements to 0
            for (int e = 0; e < dimensions; e++){
                weightMatrix[e][e] = 0;
            }
            //add weight vectors by column to neurons as their own weight vectors
            for (int c = 0; c< dimensions; c++){
                int[] weightVector = new int [dimensions];
                for (int r = 0; r<dimensions; r++){
                    weightVector[r] = weightMatrix[r][c];
                }
                neurons[c].setWeights(weightVector);
            }
            outputScan = new FileWriter(output);
            outputScan.write(dimensions + " (Dimensions)\n");

            for (int i = 0; i < dimensions; i++){
                for (int j = 0; j < dimensions; j++){
                    outputScan.write(String.valueOf(weightMatrix[i][j] + " "));
                }
                outputScan.write('\n');

            }
            outputScan.close();
            weightScan.close();

        }

        catch(Exception e) {
            System.out.println(e);
        }

    }

    public void readExistingWeightFile(File input){
        Scanner weightScan;
        try{
            weightScan = new Scanner(input);
            int dimensions = (int) weightScan.nextInt();
            //jump past the headers of the file
            
            int [][] weightMatrix = new int[dimensions][dimensions];
            weightScan.nextLine();
                //double for loop reads one matrix
            for (int r = 0; r < dimensions; r++){
                for (int c = 0; c< dimensions; c++){
                    int curIn = weightScan.nextInt();
                    weightMatrix[r][c] = curIn;
                    

                }
                if (weightScan.hasNext()){
                    weightScan.nextLine();
                }

            }
            for (int c = 0; c< dimensions; c++){
                int[] weightVector = new int [dimensions];
                for (int r = 0; r<dimensions; r++){
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

    public void testInput(File inputFile, File outputFile){
        Scanner inputScan;
        FileWriter outputScan;
        try{
            inputScan = new Scanner(inputFile);
            int dimensions = inputScan.nextInt();
            inputScan.useDelimiter("");
            int N = (int) Math.sqrt(dimensions);
            inputScan.nextLine();
            int numImages = inputScan.nextInt();
            inputScan.nextLine();
            inputScan.nextLine();
            outputScan = new FileWriter(outputFile);
            int[][] inputMatrix = new int[N][N];
            for (int y = 0; y < numImages; y++) {

                outputScan.write("Input Pattern:\n");
                //double for loop reads one matrix and prints read in matrix image
                for (int r = 0; r < N; r++){
                    for (int c = 0; c < N; c++){
                        String curIn = inputScan.next();
                        if ("O".equals(curIn)){
                            outputScan.write('O');
                            inputMatrix[r][c] = 1;
                        }
                        else{
                            outputScan.write(' ');
                            inputMatrix[r][c] = -1;
                        }

                    }
                    outputScan.write('\n');
                    if (inputScan.hasNext()){
                        inputScan.nextLine();
                    }

                }
                if (inputScan.hasNext()){
                    inputScan.nextLine();
                }
                Random random = new Random();
                int [] yin = new int[dimensions];
                        for (int r = 0; r < N; r++){
                            for (int c = 0; c<N; c++){
                                yin[(r*N)+c] = inputMatrix[r][c];
                            }
                        }
                boolean converged = false;
                int count = 0;
                while (!converged){
                    count += 1;
                    converged = true;
                    //initialize list of remaining neurons to choose from while testing
                    ArrayList<Integer> neuronsLeft= new ArrayList<>();
                    for (int i = 0; i < dimensions; i++){
                        neuronsLeft.add(i);
                    }
                    while (allElementsNotZero(neuronsLeft)){
                        //randomly select neuron
                        int index = random.nextInt(neuronsLeft.size());
                        int nextIndex = neuronsLeft.get(index);
                        neuronsLeft.remove(index);
                        //test selected neuron
                        neuron curNeuron = neurons[nextIndex];
                        int x = inputMatrix[nextIndex/N][nextIndex%N];
                        int answer = curNeuron.calcAnswer(yin, x);
                        //if undecided, keep old value and doesn't converge
                        if (answer == 0){
                            converged = false;
                        }
                        //if diff value, change yin and doesn't converge
                        else if (answer != yin[nextIndex]){
                            converged = false;
                            yin[nextIndex] = answer;
                        }
                        //otherwise change nothing and go to next neuron with same yin

                    }
                }
                //write out pattern recognized to file
                outputScan.write("\nPattern Recognized:\n");
                for (int i = 0; i < N; i++){
                    for (int j = 0; j < N; j++){
                        if (yin[(i*N)+j] == 1){
                            outputScan.write('O');
                        }
                        else if (yin[(i*N)+j] == -1){
                            outputScan.write(' ');
                        }
                    }
                    outputScan.write('\n');
                }
                outputScan.write("\nNumber of epochs: "+count+"\n\n");
            }
            outputScan.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

}
