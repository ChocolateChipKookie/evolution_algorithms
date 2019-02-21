/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions.doublearray;

import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class DoubleArraySolution extends SingleObjectiveSolution{
    public double[] values;
    
    public DoubleArraySolution(int size){
        values = new double[size];
    }
    
    private DoubleArraySolution(){}
    
    public DoubleArraySolution newLikeThis(){
        DoubleArraySolution res = new DoubleArraySolution();
        res.values = values.clone();
        return res;
    }
    
    public DoubleArraySolution duplicate(){
        return this;
    }
    
    public void randomize(Random rand, double[] mins, double[] maxs){
        for(int i = 0; i < values.length; ++i){
            values[i] = (maxs[i] - mins[i])*rand.nextDouble() + mins[i];
        }
    }
}
