/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.doublearray;

import java.util.Random;

/**
 *
 * @author Adi
 */
public class SelectiveDoubleArrayNormNeighbourhood extends DoubleArrayNormNeighbourhood{
 
    protected double[] chances;
    
    public SelectiveDoubleArrayNormNeighbourhood(double[] deltas, double[] chances, Random rand){
        super(deltas, rand);
        this.chances = chances;
    }
    
    @Override
    public DoubleArraySolution randomNeighbour(DoubleArraySolution solution){
        DoubleArraySolution res = solution.newLikeThis();
        
        for(int i = 0; i < solution.values.length; ++i){
            if(rand.nextDouble() < chances[i]){
                res.values[i] +=  rand.nextGaussian() * sigma * 2 * deltas[i];
            }       
        }
        
        return res;
    }
    
}
