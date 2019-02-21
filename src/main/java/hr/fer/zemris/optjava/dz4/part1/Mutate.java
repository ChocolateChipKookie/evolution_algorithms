/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.algorithms.genetic.IMutate;
import hr.fer.zemris.optjava.solutions.doublearray.DoubleArraySolution;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class Mutate implements IMutate<DoubleArraySolution>{
    private Random rand;
    private double sigma;
    
    
    public Mutate(double sigma, Random rand){
        this.rand = rand;
        this.sigma = sigma;
    }
    
    @Override
    public DoubleArraySolution mutate(DoubleArraySolution child) {
        DoubleArraySolution res = child.newLikeThis();
        for(int i = 0; i<res.values.length; ++i){
            res.values[i] += rand.nextGaussian() * sigma;
        }
        return res;
    }
    
}
