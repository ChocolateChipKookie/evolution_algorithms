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
public class SelectiveMutate implements IMutate<DoubleArraySolution>{

    private Random rand;
    private double sigma;
    private double[] mutationFactors;
    private double mutationFactor;
    private boolean oneFactor;
    
    public SelectiveMutate(double sigma, double[] mutationFactors , Random rand){
        this.rand = rand;
        this.sigma = sigma;
        this.mutationFactors = mutationFactors;
        oneFactor = false;
    }

    public SelectiveMutate(double sigma, double mutationFactor , Random rand){
        this.rand = rand;
        this.sigma = sigma;
        this.mutationFactor = mutationFactor;
        oneFactor = true;
    }
    
    @Override
    public DoubleArraySolution mutate(DoubleArraySolution child) {
        DoubleArraySolution res = child.newLikeThis();
        
        if(oneFactor){
            for(int i = 0; i<res.values.length; ++i){
                if(rand.nextDouble()<mutationFactor){
                    res.values[i] += rand.nextGaussian() * sigma;                
                }
            }    
        }
        else{
            for(int i = 0; i<res.values.length; ++i){
                if(rand.nextDouble()<mutationFactors[i]){
                    res.values[i] += rand.nextGaussian() * sigma;                
                }
            }
        }
        return res;
    }
}
