package hr.fer.zemris.optjava.dz7;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.FFANN;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.ITransferFunction;

import java.util.Arrays;

public class ElmanNeuralNetwork extends FFANN {
    private double[] contextNodes;
    private int contextNodeVectorWeights;


    /**
     * @param layers            Number of nodes per layer(input and output layer included)
     * @param transferFunctions Transfer Functions for every layer
     */
    public ElmanNeuralNetwork(int[] layers, ITransferFunction[] transferFunctions) {
        super(layers, transferFunctions);
        this.contextNodes = new double[layers[1]];
        this.contextNodeVectorWeights = layers[1]*layers[1];
    }

    @Override
    public double[] valueAt(double[] input, double[] weights) {
        double[] output = new double[layers[1]];

        for(int i = 0; i < layers[1]; ++i){
            double[] w = getWeights(1, i, weights);
            double net = w[0];
            for(int j = 0; j < input.length; ++j){
                net += input[j] * w[j + 1];
            }
            for(int j = 0; j < layers[1]; ++j){
                net += contextNodes[j] * weights[weightVectorSize + i * layers[1] + j];
            }

            //Call transfer function
            output[i] = transferFunctions[0].transferFunction(net);
        }

        contextNodes = output.clone();
        for(int i = 2; i < layers.length; ++i){
            output = nodeOutputs(output, weights, i);
        }

        return output;
    }

    @Override
    public int getWeightVectorLength(){
        return super.weightVectorSize + contextNodeVectorWeights;
    }

    @Override
    public int getSolutionVectorSize(){
        return getWeightVectorLength() + contextNodes.length * contextNodes.length;
    }

    public double[] getContextNodes(){
        return contextNodes.clone();
    }

    public void setContextNodes(double[] contextNodes){
        this.contextNodes = contextNodes.clone();
    }

    @Override
    public double[] getOutput(double[] input, double[] solution){
        double[] tmp = contextNodes.clone();

        System.arraycopy(solution, getWeightVectorLength(), contextNodes,0, contextNodes.length);
        double[] result = valueAt(input, solution);
        contextNodes = tmp;

        return result;
    }

    @Override
    public double[] getSolution(double[] solution){
        double[] result = new double[getSolutionVectorSize()];
        System.arraycopy(solution, 0, result,0, solution.length);
        System.arraycopy(contextNodes, 0, result,getWeightVectorLength(), contextNodes.length);
        return result;
    }
}
