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
public class SelectiveDoubleArrayUnifNeighbourhood extends DoubleArrayUnifNeighbourhood {
    protected double[] chances;
    
    public SelectiveDoubleArrayUnifNeighbourhood(double[] deltas, double[] chances, Random rand){
        super(deltas, rand);
        this.chances = chances;
    }
    
    @Override
    public DoubleArraySolution randomNeighbour(DoubleArraySolution solution){
        DoubleArraySolution res = solution.newLikeThis();
        
        for(int i = 0; i < solution.values.length; ++i){
            if(rand.nextDouble() < chances[i]){
                res.values[i] +=  (rand.nextDouble() - 0.5) * 2 * deltas[i];
            }       
        }
        
        return res;
    }
}
