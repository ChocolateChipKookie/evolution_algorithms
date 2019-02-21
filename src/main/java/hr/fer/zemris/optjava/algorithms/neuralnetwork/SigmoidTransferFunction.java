/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.algorithms.neuralnetwork;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.ITransferFunction;

/**
 *
 * @author Adi
 */
public class SigmoidTransferFunction implements ITransferFunction {

    @Override
    public double transferFunction(double net) {
        return 1./(1 + Math.exp(-net));
    }
}
