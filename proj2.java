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
        discretehopfield hopfieldNet = new discretehopfield(100, 100);
        System.out.println("Welcome to my Discrete Hopfield Neural Net!");
        boolean rerun = true;
        while (rerun){
            System.out.println("\nEnter 1 to train using a training data file");//\nEnter 2 to use pretrained weights");
            int userChoice = scan.nextInt();
            while (userChoice > 2 || userChoice < 1){
                System.out.println("invalid entry, please enter 1 or 2");
                userChoice = scan.nextInt();
            }
            if (userChoice == 1){
                System.out.println("Enter the training data file name: ");
                String trainingFile = scan.next();
                
                //We could leave out saving weight to file and just directly use weights read from input file, saves lot of file reading/writing code
                System.out.println("Enter a filename to save weights to: ");
                String weightFile = scan.next();
                //function for weight training here
                hopfieldNet.readWeightFile(new File(trainingFile), new File(weightFile));

            }
            //else if (userChoice == 2){
                //System.out.println("Enter the trained weight settings input data file name:");
                //String weightFileData = scan.next();
                //hopfieldNet.readWeightFile(new File(weightFileData));

                //function the reads weight file here
            //}
            System.out.println("Enter 1 to test using a testing data file, enter 2 to quit: ");
            userChoice = scan.nextInt();
            if (userChoice == 1){
                
                try{
                    System.out.println("Enter name of test file: ");
                    String testFileName = scan.next();
                    File testFile = new File(testFileName);
                    System.out.println("Enter name of the output file: ");
                    String outputFileName = scan.next();
                    File outputFile = new File(outputFileName);
                
                    hopfieldNet.testInput(testFile, outputFile);
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