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
    public void readWeightFile(File input){
        Scanner weightScan;
        try{
            weightScan = new Scanner(input);
            //jump past the headers of the file
            weightScan.nextInt();
            weightScan.nextInt();
            while (weightScan.hasNext()) {
                int[][] cur_matrix = new int[10][10];
                for (int r = 0; r < 10; r++){
                    for (int c = 0; c<10; c++){
                        String cur_in = weightScan.next();
                        if (cur_in == "0"){
                            
                        }
                        else{

                        }

                    }

                }
                }

            }
        }
        catch(Exception e) {
            System.out.println(e);
        }

    }

}
