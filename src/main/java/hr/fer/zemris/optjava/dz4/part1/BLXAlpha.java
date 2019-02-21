/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.algorithms.genetic.ICrossover;
import hr.fer.zemris.optjava.solutions.doublearray.DoubleArraySolution;
import java.util.Random;

/**
 *
 * @author Adi
 * @param <DoubleArraySolution>
 */
public class BLXAlpha implements ICrossover<DoubleArraySolution>{
    private Random rand;
    
    public BLXAlpha(Random rand){
        this.rand = rand;
    }
    
    @Override
    public DoubleArraySolution CreateOffspring(DoubleArraySolution mother, DoubleArraySolution father) {
        DoubleArraySolution child = new DoubleArraySolution(mother.values.length);
        
        for(int i = 0; i < child.values.length; ++i){
            double min = Math.min(mother.values[i], father.values[i]);
            double max = Math.max(mother.values[i], father.values[i]);
            
            child.values[i] = min + (max - min)*rand.nextDouble();
        }
        return child;
    }
}
