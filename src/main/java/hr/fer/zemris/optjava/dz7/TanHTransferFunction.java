package hr.fer.zemris.optjava.dz7;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.ITransferFunction;

public class TanHTransferFunction implements ITransferFunction {

    @Override
    public double transferFunction(double net) {
        double eToMinusNet = Math.exp(-net);
        return (1 - eToMinusNet)/(1 + eToMinusNet);
    }
}
