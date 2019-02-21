package hr.fer.zemris.optjava.dz7;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.IFunction;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.FFANN;
import hr.fer.zemris.optjava.dz6.IrisData;

import javax.xml.crypto.Data;

public class Function implements IFunction {

    protected FFANN neuralNetwork;
    private Dataset dataset;

    public Function(FFANN neuralNetwork, Dataset dataset){
        this.neuralNetwork = neuralNetwork;
        this.dataset = dataset;
    }

    @Override
    public double valueAt(double[] point) {
        double[] result;
        double datasetSum = 0;
        for(int i = 0; i < dataset.size(); ++i){
            DataSample d = dataset.getData(i);
            result = neuralNetwork.valueAt(d.inputs, point);
            //Zbroj kvadrata razlike
            double tmp = d.output - result[0];
            datasetSum += tmp*tmp;
        }
        return datasetSum/dataset.size();
    }

    @Override
    public int solutionSize() {
        return neuralNetwork.getSolutionVectorSize();
    }


}
