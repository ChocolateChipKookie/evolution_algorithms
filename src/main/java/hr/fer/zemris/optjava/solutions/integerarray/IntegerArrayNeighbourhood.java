/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.integerarray;

import hr.fer.zemris.optjava.solutions.INeighbourhood;
import java.lang.reflect.Array;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class IntegerArrayNeighbourhood implements INeighbourhood<IntegerArraySolution>{
    protected int[] deltas;
    protected Random rand;
    
    public IntegerArrayNeighbourhood(IntegerDecoder decoder, double[] deltas, Random rand){
        this.deltas = new int[decoder.maxs.length];
        for(int i = 0; i<decoder.mins.length; ++i){
            this.deltas[i] =(int)(deltas[i]/(decoder.maxs[i] - decoder.mins[i])*((double)Integer.MAX_VALUE));
        }
        this.rand = rand;
    }
    
    public IntegerArrayNeighbourhood(IntegerDecoder decoder, double delta, Random rand){
        this.deltas = new int[decoder.maxs.length];
        for(int i = 0; i<decoder.mins.length; ++i){
            this.deltas[i] =(int)(delta/(decoder.maxs[i] - decoder.mins[i])*((double)Integer.MAX_VALUE));
        }
        this.rand = rand;
    }
    
    @Override
    public IntegerArraySolution randomNeighbour(IntegerArraySolution current) {
        IntegerArraySolution res = current.newLikeThis();
        
        for(int i = 0; i < res.ints.length; ++i){
            res.ints[i] += rand.nextInt(2*deltas[i]) - deltas[i];            
        }
        return res;
    }
    
}
