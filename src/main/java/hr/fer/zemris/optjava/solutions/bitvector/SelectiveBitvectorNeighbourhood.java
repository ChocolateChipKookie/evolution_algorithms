/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.bitvector;

import java.util.Random;

/**
 *
 * @author Adi
 */
public class SelectiveBitvectorNeighbourhood extends BitvectorNeighbourhood{
    
    private double[] chances;
    
    public SelectiveBitvectorNeighbourhood(BitvectorDecoder decoder, double[] chances, Random rand){
        super(decoder, rand);
        this.chances = chances;
    }
    
    @Override
    public BitvectorSolution randomNeighbour(BitvectorSolution current) {
        int index = 0;
        BitvectorSolution res = current.newLikeThis();
        
        for(int i = 0; i < bits.length; ++i){
            if(rand.nextDouble() < chances[i]){
                int pos = rand.nextInt(bits[i]);
                res.bits[index + pos] = !res.bits[index + pos];
                index += bits[i];
            }
        }
        return res;
    }
            
}
