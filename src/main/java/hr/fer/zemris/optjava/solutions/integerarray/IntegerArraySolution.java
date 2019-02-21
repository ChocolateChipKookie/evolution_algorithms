/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.integerarray;

import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class IntegerArraySolution extends SingleObjectiveSolution {
    public int[] ints;
    
    public IntegerArraySolution(int size){
        ints = new int[size];
    }

    private IntegerArraySolution(){}
    
    public IntegerArraySolution newLikeThis(){
        IntegerArraySolution res = new IntegerArraySolution();
        res.ints = ints.clone();
        return res;
    }
    
    public IntegerArraySolution duplicate(){
        return this;
    }
    
    public void randomize(Random rand){
        for(int i = 0; i < ints.length; ++i){
            ints[i] = rand.nextInt();
        }
    }
}
