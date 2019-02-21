/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.bitvector;

import hr.fer.zemris.optjava.solutions.INeighbourhood;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class BitvectorNeighbourhood implements INeighbourhood<BitvectorSolution>{

    protected int[] bits;
    Random rand;
    
    public BitvectorNeighbourhood(BitvectorDecoder decoder, Random rand){
        this.bits = decoder.bits;
        this.rand = rand;
    }
    
    @Override
    public BitvectorSolution randomNeighbour(BitvectorSolution current) {
        int index = 0;
        BitvectorSolution res = current.newLikeThis();
        
        for(int i = 0; i < bits.length; ++i){
            int pos = rand.nextInt(bits[i]);
            res.bits[index + pos] = !res.bits[index + pos];
            index += bits[i];
        }
        return res;
    }
    
}
