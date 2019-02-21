/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.doublearray;

import hr.fer.zemris.optjava.solutions.IDecoder;

/**
 *
 * @author Adi
 */
public class PassThroughDecoder implements IDecoder<DoubleArraySolution>{
    
    @Override
    public double[] decode(DoubleArraySolution solution){
        return solution.values;
    }


    @Override    
    public void decode(DoubleArraySolution solution, double[] result){
        result = solution.values;
    }
    
}
