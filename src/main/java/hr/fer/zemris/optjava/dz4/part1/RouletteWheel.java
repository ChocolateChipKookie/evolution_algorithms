/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.algorithms.genetic.IMutate;
import hr.fer.zemris.optjava.algorithms.genetic.ICrossover;
import hr.fer.zemris.optjava.algorithms.genetic.ISelection;
import hr.fer.zemris.optjava.algorithms.IFunction;
import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;
import hr.fer.zemris.optjava.solutions.doublearray.DoubleArraySolution;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class RouletteWheel implements ISelection<SingleObjectiveSolution>{
    private final Random rand;
    private final ICrossover crossover;
    private final IMutate mutate;
    
    public RouletteWheel(Random rand, ICrossover crossover, IMutate mutate){
        this.rand = rand;
        this.crossover = crossover;
        this.mutate = mutate;
    }
    
    @Override
    public SingleObjectiveSolution[] select(SingleObjectiveSolution[] currentIndividuals) {
        SingleObjectiveSolution[] result = new SingleObjectiveSolution[currentIndividuals.length];
        
        double minFitness = Double.MAX_VALUE;
        for(int i = 0; i < currentIndividuals.length; ++i){
            if(currentIndividuals[i].fitness < minFitness)minFitness = currentIndividuals[i].fitness; 
        }
        
        double fitnessSum = 0;
        for(int i = 0; i < currentIndividuals.length; ++i){
            currentIndividuals[i].fitness -= minFitness;
            fitnessSum += currentIndividuals[i].fitness;
        }
        
        for(int i = 0; i < currentIndividuals.length; ++i){
            int mother = currentIndividuals.length - 1;
            int father = mother;
            for(int j = 0; j<currentIndividuals.length; ++j){
                double tmp = rand.nextDouble() * fitnessSum;
                tmp -= currentIndividuals[j].fitness;
                if(tmp < 0){
                    mother = j;
                }
            }
            for(int j = 0; j<currentIndividuals.length; ++j){
                double tmp = rand.nextDouble() * fitnessSum;
                tmp -= currentIndividuals[j].fitness;
                if(tmp < 0){
                    father = j;
                }
            }
            
            result[i] = (SingleObjectiveSolution)mutate.mutate(crossover.CreateOffspring(currentIndividuals[mother], currentIndividuals[father]));
            }
        
        return result;
    }
}
