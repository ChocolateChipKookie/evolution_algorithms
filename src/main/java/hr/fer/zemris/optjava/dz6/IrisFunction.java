/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.FFANN;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.IFunction;

/**
 *
 * @author Adi
 */
public class IrisFunction implements IFunction {

    private FFANN neuralNetwork;
    private IrisDataset dataset;
    
    public IrisFunction(FFANN neuralNetwork, IrisDataset dataset){
        this.neuralNetwork = neuralNetwork;
        this.dataset = dataset;
    }

    @Override
    public double valueAt(double[] point) {
        double[] result;
        double datasetSum = 0;
        for(int i = 0; i < dataset.size(); ++i){
            IrisData id = dataset.getData(i);
            result = neuralNetwork.valueAt(id.inputs, point);
            //Zbroj kvadrata razlike
            double sum = 0;
            for(int j = 0; j < result.length; ++j){
                double tmp = dataset.getData(i).outputs[j] - result[j];
                sum += tmp*tmp;
            }
            datasetSum += sum;
        }
        return datasetSum/dataset.size();
    }

    @Override
    public int solutionSize() {
        return neuralNetwork.getWeightVectorLength();
    }

    public void testSolution(double[] weight, boolean printSolution){
        int good = 0;
        for(int i =0; i < dataset.size(); ++i){
            double[] result = neuralNetwork.valueAt(dataset.getData(i).inputs, weight);
            int[] res = new int[result.length];

            boolean isSame = true;
            for(int j = 0; j < res.length; ++j){
                if(result[j] > 0.5){
                    res[j] = 1;
                }
                else{
                    res[j] = 0;
                }
                if(res[j] != (int)dataset.getData(i).outputs[j]){
                    isSame = false;
                }
            }

            if(printSolution){
                System.out.print(i + " ");
                for(int j = 0; j < res.length; ++j){
                    System.out.print(String.format("%.2f ", result[j]));
                }
                System.out.println(isSame);
            }
            if(isSame)good++;
        }
        System.out.println(good + "/" + dataset.size());
    }
}
