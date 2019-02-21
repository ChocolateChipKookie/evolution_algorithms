/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz4.part2;

import hr.fer.zemris.optjava.solutions.IDecoder;

/**
 *
 * @author Adi
 */
public class PositionVectorDecoder implements IDecoder<PositionVectorSolution>{


    @Override
    public double[] decode(PositionVectorSolution value) {
        double[] res = new double[value.positions.length];
        for(int i = 0; i < value.positions.length; ++i){
            res[i] = (double) value.positions[i];
        }
        return res;
    }

    @Override
    public void decode(PositionVectorSolution value, double[] result) {
        result = decode(value);
    }
}
