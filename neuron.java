/*
 * Authors: Daniel Daugbjerg, Kent Morris, Casey Klutznick, Thomas McKeown
 * Date Last Modified: 3/7/24
 * Description: Neuron class that handles weights and training
 */ 

public class neuron{
    double[] weights;
    int neuronNum;//for numbering neurons for selection etc.
    
    public neuron(int numInputs, int num){
        weights = new double[numInputs];
        neuronNum = num;
    }

    public void createWeights(){
        for (int i=0; i<weights.length; i++){
            weights[i] = 0;
        }
    }

    //not needed, will train weights in separate file, then set those weights for all neurons with setWeights()
    /* 
    public boolean trainNeuron(int[] inputs, float answer, double alpha, double theta){
        double neurAns = 0;
        int y;
        boolean noChange = true;
        for (int i=0; i<inputs.length; i++){
            neurAns += inputs[i]*weights[i];
        }
        if (neurAns > theta){
            y = 1;
        }
        else if (neurAns < -theta){
            y = -1;
        }
        else {
            y = 0;
        }
        //account for very similar value/imperfect convergence
        if (y != answer){
            for (int i = 0; i < inputs.length; i++) {
                if (answer*inputs[i]*alpha > weight_threshold) {
                    noChange = false;
                    weights[i] = weights[i] + answer * inputs[i] * alpha;
                }
            }
        }
        return noChange;
    }
    */

    //this is assuming we input right weight column and x value
    public int calcAnswer(int[] yins, int x, int[] weights){
        int yin = x;
        for (int i=0; i<weights.length; i++){
            yin += yins[i] * weights[i];
        }
        if (yin == 0){
            return yins[neuronNum]; //previous yin
        }
        else if (yin > 0){
            return 1;
        }
        else{
            return -1;
        }
    }

    public double[] getWeights(){
        return weights;
    }

    public int getNum(){
        return neuronNum;
    }

    public void setWeights(double[] winput){
        weights = winput;
    }

}
