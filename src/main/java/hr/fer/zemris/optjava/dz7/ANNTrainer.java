package hr.fer.zemris.optjava.dz7;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.FFANN;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.ITransferFunction;

public class ANNTrainer {

    public static void main(String[] args){
        String[] tmp = args[1].split("-");
        String[] arhi = tmp[1].split("x");
        //elman-1x3x5x1
        //tdnn-6x3x5x1
        int[] arh = new int[arhi.length];

        for(int i = 0; i < arhi.length; ++i){
            arh[i] = Integer.parseInt(arhi[i]);
        }

        Dataset dataset = new Dataset(args[0], arh[0], 600);
        Dataset datasetTest = new Dataset(args[0], arh[0], -1);
        int n = Integer.parseInt(args[2]);
        double maxError = Double.parseDouble(args[3]);
        int maxIter = Integer.parseInt(args[4]);

        ITransferFunction[] tf = new ITransferFunction[arh.length - 1];
        for(int i = 0; i < arh.length - 1; ++i){
            tf[i] = new TanHTransferFunction();
        }

        FFANN nn = null;
        switch (tmp[0]){
            case "elman":
                nn = new ElmanNeuralNetwork(arh, tf);
                break;
            case "tdnn":
                nn = new FFANN( arh, tf);
        }

        Function func = new Function(nn, dataset);
        DiffEvo de = new DiffEvo(func, maxIter, n, -3, 3, 0.7, 0.20, maxError);
        de.run();

        Function funcTest = new Function(nn, datasetTest);
        System.out.println("Final total error: " + funcTest.valueAt(de.getSolutionVector()));
    }
}
