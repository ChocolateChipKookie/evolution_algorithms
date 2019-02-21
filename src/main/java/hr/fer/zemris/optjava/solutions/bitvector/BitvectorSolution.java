/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.bitvector;

import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class BitvectorSolution extends SingleObjectiveSolution{
    public boolean[] bits;
    
    public BitvectorSolution(int size){
        bits = new boolean[size];
    }

    private BitvectorSolution(){}
    
    public BitvectorSolution newLikeThis(){
        BitvectorSolution res = new BitvectorSolution();
        res.bits = bits.clone();
        return res;
    }
    
    public BitvectorSolution duplicate(){
        return this;
    }
    
    public void randomize(Random rand){
        for(int i = 0; i < bits.length; ++i){
            bits[i] = rand.nextBoolean();
        }
    }
}
