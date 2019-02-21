/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.doublearray;

import hr.fer.zemris.optjava.solutions.INeighbourhood;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class DoubleArrayUnifNeighbourhood implements INeighbourhood<DoubleArraySolution>{

    protected double[] deltas;
    protected Random rand;
    
    public DoubleArrayUnifNeighbourhood(double[] deltas, Random rand){
        this.deltas = deltas.clone();
        this.rand = rand;
    }
    
    @Override
    public DoubleArraySolution randomNeighbour(DoubleArraySolution solution){
        DoubleArraySolution res = solution.newLikeThis();
        
        for(int i = 0; i < solution.values.length; ++i){
            res.values[i] +=  (rand.nextDouble() - 0.5) * 2 * deltas[i];            
        }
        return res;
    }
    
    
}
