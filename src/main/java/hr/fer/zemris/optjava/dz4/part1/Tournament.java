/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.algorithms.genetic.IMutate;
import hr.fer.zemris.optjava.algorithms.genetic.ICrossover;
import hr.fer.zemris.optjava.algorithms.genetic.ISelection;
import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class Tournament implements ISelection<SingleObjectiveSolution>{
    
    private final int n;
    private final Random rand;
    private final ICrossover crossover;
    private final IMutate mutate;
    
    public Tournament(int n, Random rand, ICrossover crossover, IMutate mutate){
        this.n = n;
        this.rand = rand;
        this.crossover = crossover;
        this.mutate = mutate;
    }

    @Override
    public SingleObjectiveSolution[] select(SingleObjectiveSolution[] currentIndividuals) {
        List<Integer> indexes = new ArrayList<>();
        for(int i = 0; i<n; i++){
            int index = rand.nextInt(currentIndividuals.length);
            
            while(true){
                if(!indexes.contains(index)){
                    indexes.add(index);
                    break;
                }
                else{
                    index = (++index)%currentIndividuals.length;
                }
            }
        }
        
        indexes.sort((Integer t, Integer t1) -> {
            if(currentIndividuals[t].fitness < currentIndividuals[t1].fitness) return 1;
            else return -1;
        });
        
        SingleObjectiveSolution child = (SingleObjectiveSolution) crossover.CreateOffspring(currentIndividuals[indexes.get(0)], currentIndividuals[indexes.get(1)]);
        currentIndividuals[indexes.get(indexes.size() - 1)] = (SingleObjectiveSolution) mutate.mutate(child);
        
        return currentIndividuals;
    }
}
