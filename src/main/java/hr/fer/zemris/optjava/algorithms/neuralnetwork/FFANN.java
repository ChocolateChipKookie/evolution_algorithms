/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.algorithms.neuralnetwork;


/**
 *
 * @author Adi
 */
public class FFANN{
    
    
    protected int[] layers;
    protected final int weightVectorSize;
    protected ITransferFunction[] transferFunctions;
    
    /**
     * 
     * @param layers Number of nodes per layer(input and output layer included)
     * @param transferFunctions Transfer Functions for every layer
     */ 
    public FFANN(int[] layers, ITransferFunction[] transferFunctions){
        this.layers = layers;
        int numberOfWeights = 0;
        for(int i = 1; i < layers.length; ++i){
            numberOfWeights += (1 + layers[i-1])* layers[i];
        }
        weightVectorSize = numberOfWeights;
        
        if(transferFunctions.length != layers.length - 1){
            throw new RuntimeException("Wrong number of transfer functions!");
        }
        this.transferFunctions = transferFunctions;
    }
    
    public double[] getWeights(int layer, int node, double[] weights){
        double[] result = new double[layers[layer - 1] + 1];
        
        int layerStart = 0;
        for(int i = 1; i < layer; ++i){
            layerStart += (1 + layers[i-1])* layers[i];
        }
        
        int nodeStart = layerStart + (layers[layer - 1] + 1)*node;
        for(int i = 0, k = layers[layer - 1] + 1; i < k; ++i){
            result[i] = weights[nodeStart + i];
        }

        return result;
    }
    
    public int getWeightVectorLength(){
        return weightVectorSize;
    }

    public int getSolutionVectorSize(){
        return getWeightVectorLength();
    }

    public double[] valueAt(double[] input, double[] weights) {
        double[] output = input.clone();
        for(int i = 1; i < layers.length; ++i){
            output = nodeOutputs(output, weights, i);
        }
        return output;
    }
    
    protected double[] nodeOutputs(double[] inputs, double[] weights, int layer){
        double[] result = new double[layers[layer]];
        
        for(int i = 0; i < layers[layer]; ++i){
            //Get weights for node i in layer
            double[] w = getWeights(layer, i, weights);
            //Personal weigth
            double net = w[0];
            
            //Sum Input[i]*InputWeight[i]
            //InputWeight[i] = w[i + 1]
            for(int j = 0; j < inputs.length; ++j){
                net += inputs[j] * w[j + 1];
            }
            //Call transfer function
            result[i] = transferFunctions[layer - 1].transferFunction(net);
        }
        return result;
    }

    public double[] getOutput(double[] input, double[] solution){
        return valueAt(input, solution);
    }

    public double[] getSolution(double[] solution){
        return solution;
    }

}
