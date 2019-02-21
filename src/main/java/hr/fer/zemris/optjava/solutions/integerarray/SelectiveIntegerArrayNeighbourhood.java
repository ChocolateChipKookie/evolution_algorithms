/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.integerarray;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class SelectiveIntegerArrayNeighbourhood extends IntegerArrayNeighbourhood{
    private double[] chances;
    
    public SelectiveIntegerArrayNeighbourhood(IntegerDecoder decoder, double[] deltas, double[] chances, Random rand){
        super(decoder, deltas, rand);
        this.chances = chances;
    }
    
    public SelectiveIntegerArrayNeighbourhood(IntegerDecoder decoder, double delta, double chance, Random rand){
        super(decoder, delta, rand);
        this.chances = new double[decoder.maxs.length];
        Arrays.fill(chances, chance);
    }
    
    @Override
    public IntegerArraySolution randomNeighbour(IntegerArraySolution current) {
        IntegerArraySolution res = current.newLikeThis();
        
        for(int i = 0; i < res.ints.length; ++i){
            if(rand.nextDouble() < chances[i]){
                res.ints[i] += rand.nextInt(2*deltas[i]) - deltas[i];            
            }
        }
        return res;
    }
}
