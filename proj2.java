/*
 * Authors: Daniel Daugbjerg, Kent Morris, Casey Klutznick, Thomas McKeown
 * Date Last Modified: 3/7/24
 * Description: Main java file used for getting user input in order to interact with the hopfield machine
 */
import java.util.Scanner;
import java.io.*;

 class proj2{

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        discretehopfield hopfieldNet = new discretehopfield(10, 10);
        System.out.println("Welcome to my Discrete Hopfield Neural Net!");
        boolean rerun = true;
        while (rerun){
            System.out.println("\nEnter 1 to train using a training data file\nEnter 2 to use pretrained weights");
            int userChoice = scan.nextInt();
            while (userChoice > 2 || userChoice < 1){
                System.out.println("invalid entry, please enter 1 or 2");
                userChoice = scan.nextInt();
            }
            if (userChoice == 1){
                System.out.println("Enter the training data file name: ");
                String trainingFile = scan.next();
                
                //We could leave out saving weight to file and just directly use weights read from input file, saves lot of file reading/writing code
                //System.out.println("Enter a filename to save weights to: ");
                
                //function for weight training here
                hopfieldNet.readWeightFile(new File(trainingFile));

            }
            else if (userChoice == 2){
                System.out.println("Enter the trained weight settings input data file name:");
                String weightFileData = scan.next();
                hopfieldNet.readWeightFile(new File(weightFileData));

                //function the reads weight file here
            }
            System.out.println("Enter 1 to test using a testing data file, enter 2 to quit: ");
            userChoice = scan.nextInt();
            if (userChoice == 1){
                
                try{
                    System.out.println("Enter name of test file: ");
                    String testFileName = scan.next();
                    int[] inOut = getInputAndOutputSize(testFileName, 3);
                    File testFile = new File(testFileName);
                    Scanner testScan = new Scanner(testFile);

                    System.out.println("Enter name of the output file: ");
                    String outputFileName = scan.next();
                    File outputFile = new File(outputFileName);
                    FileWriter writer = new FileWriter(outputFile);
                
                    //skip headers
                    testScan.nextInt();
                    int output_len = testScan.nextInt();
                    testScan.nextInt();
                    //loop for each whole input
                    for (int i = 0; i < inOut[2]; i++){
                        int [] answerVector = new int[inOut[1]];
                        //answerVector = perceptron.testNet(testScan);
                        writer.write("Answer Vector:\n");
                        for (int c = 0; c < answerVector.length; c++) {
                            writer.write(Integer.toString(answerVector[c])); // Convert int to string
                            if (c < answerVector.length - 1) {
                                writer.write(" ");
                            }
                        }
                        writer.write('\n');
                        writer.write("Expected Vector:\n");
                        //String line = testScan.nextLine();
                        int [] correctAnswerVector = new int[inOut[1]];
                        for (int j = 0; j < output_len; j++){
                            correctAnswerVector[j] = testScan.nextInt();
                            writer.write(Integer.toString(correctAnswerVector[j])); 
                            if (j < output_len-1) {
                                writer.write(" ");
                            }
                        }
                        writer.write('\n');
                        testScan.nextLine();
                        String answerLetter = testScan.nextLine();
                        writer.write("Letter being used: " + answerLetter + "\n");
                        //check for match
                        boolean match = true;
                        int positiveOnes = 0;
                        for (int j = 0; j < answerVector.length; j++){
                            if (answerVector[j] != correctAnswerVector[j]){
                                match = false;
                            }
                            if (answerVector[j] == 1){
                                positiveOnes++;
                            }
                        }
                        if (positiveOnes != 1){
                            writer.write("Undecided answer\n\n");
                        }
                        else if (match == false){
                            writer.write("Did not recognize character properly\n\n");
                        }
                        else{
                            writer.write("Character recognized\n\n");
                        }
                        
                    }
                    writer.close();
                    testScan.close();
                }
                catch (Exception e){
                    System.out.println(e);
                    e.printStackTrace();
                    System.exit(1);
                }
                //implement testing call here
            }
           
            System.out.println("Would you like to run the program again (Enter y for yes or n for no):");
            String decision = scan.next();
            if (decision.equalsIgnoreCase("n")){
                rerun = false;
            }   
        }
    }

    public static int[] getInputAndOutputSize(String filename, int headerSize){
        int[] inputOutput = new int[3];
        try {
            BufferedReader fileScan = new BufferedReader(new FileReader(filename));
            inputOutput[0] = Integer.parseInt(fileScan.readLine());
            inputOutput[1] = Integer.parseInt(fileScan.readLine());
            if (headerSize == 3) {
                inputOutput[2] = Integer.parseInt(fileScan.readLine());
            }
        }
        catch (Exception e){
            System.out.println("File not found. Exiting");
            System.exit(1);
        }
        return inputOutput;
    }
}