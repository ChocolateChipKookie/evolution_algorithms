/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz4.part2;

import hr.fer.zemris.optjava.algorithms.genetic.IMutate;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class PositionVectorMutate implements IMutate<PositionVectorSolution>{

    private double mutationChance;
    private Random rand;
    
    public PositionVectorMutate(double mutationChance, Random rand){
        this.mutationChance = mutationChance;
        this.rand = rand;
    }
    
    @Override
    public PositionVectorSolution mutate(PositionVectorSolution child) {
        while(rand.nextDouble() < mutationChance){
            int i1 = rand.nextInt(child.positions.length);
            int i2 = rand.nextInt(child.positions.length);
            
            int tmp = child.positions[i1];
            child.positions[i1] = child.positions[i2];
            child.positions[i2] = tmp;
        }
        return child;
    }
    
}
