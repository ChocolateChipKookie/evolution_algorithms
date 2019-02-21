/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz4.part2;

import hr.fer.zemris.optjava.algorithms.IFunction;
import hr.fer.zemris.optjava.algorithms.IOptAlgorithm;
import hr.fer.zemris.optjava.algorithms.genetic.ISelection;
import hr.fer.zemris.optjava.dz4.part1.Tournament;
import hr.fer.zemris.optjava.solutions.IDecoder;
import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class GeneticAlgorithm implements IOptAlgorithm{
    
    static public void main(String[] args){
        String path = args[0];
        int populationSize = Integer.parseInt(args[1]);
        int initialPopulations = Integer.parseInt(args[2]);
        Random rand = new Random();
        PositionVectorCrossover crossover = new PositionVectorCrossover(rand);
        PositionVectorMutate mutate = new PositionVectorMutate(0.5, rand);
        
        Function function = new Function(path);
        
        ISelection<SingleObjectiveSolution> select = new Tournament(4, rand, crossover, mutate);
        
        PositionVectorSolution[] startWith = new PositionVectorSolution[populationSize];
        
        for(int i = 0; i<populationSize; ++i){
            startWith[i] = new PositionVectorSolution(function.dimensions(), rand);
            startWith[i].randomise();
        }
        
        GeneticAlgorithm ga = new GeneticAlgorithm(initialPopulations, startWith, function, new PositionVectorDecoder(), select, rand);
        ga.run();
    }

    private int villageNumber;
    private final Random rand;
    private final IFunction function;
    private final ISelection selection;
    private final int GENERATIONS_BEFORE_MERGING = 600;
    private final PositionVectorSolution[] startWith;
    private final IDecoder decoder;
    
    public GeneticAlgorithm(int villageNumber, PositionVectorSolution[] startWith, IFunction function, IDecoder decoder, ISelection<SingleObjectiveSolution> selection, Random rand){
        this.villageNumber = villageNumber;
        this.startWith = startWith;
        this.function = function;
        this.selection = selection;
        this.rand = rand;
        this.decoder = decoder;
    }
    
    @Override
    public void run() {
        ArrayList<ArrayList<PositionVectorSolution>> villages = new ArrayList<>();
        
        for(int i = 0; i < villageNumber; ++i){
            villages.add(new ArrayList<>());
        }
        
        int currentVillage = 0;

        for(int i = 0; i < startWith.length; ++i){
            villages.get(currentVillage).add(startWith[i]);
            currentVillage = (++currentVillage)%villageNumber;
        }
        
        while(villageNumber > 0){
            for(ArrayList<PositionVectorSolution> village : villages){
                PositionVectorSolution[] currentGeneration = village.toArray(new PositionVectorSolution[village.size()]);
                for(int j = 0; j<GENERATIONS_BEFORE_MERGING; ++j){

                    for(int i = 0; i < currentGeneration.length; ++i){
                        currentGeneration[i].value = function.valueAt(decoder.decode(currentGeneration[i]));
                        currentGeneration[i].fitness = 1000./(currentGeneration[i].value + 1);
                    }
                    
                    SingleObjectiveSolution[] tmp = (SingleObjectiveSolution[]) selection.select(currentGeneration);
                    
                    for(int i = 0; i < tmp.length; ++i){
                        currentGeneration[i] =(PositionVectorSolution) tmp[i];
                    }
                }
                
                for(int i = 0; i < currentGeneration.length; ++i){
                    currentGeneration[i].value = function.valueAt(decoder.decode(currentGeneration[i]));
                    currentGeneration[i].fitness = 1000./(currentGeneration[i].value + 1);
                }                
                
                village.clear();
                village.addAll(Arrays.asList(currentGeneration));
            }
            
            ArrayList<ArrayList<PositionVectorSolution>> tmp_villages = new ArrayList<>();
            
            --villageNumber;
            for(int i = 0; i < villageNumber; ++i){
                tmp_villages.add(new ArrayList<>());
            }
            
            if(villageNumber == 0) break;
            
            int index = 0;
            PositionVectorSolution best = new PositionVectorSolution(0,null);
            best.fitness = 0;
            for(ArrayList<PositionVectorSolution> tmp : villages){
                for(PositionVectorSolution v : tmp){
                    tmp_villages.get(index).add(v);
                    index = (++index)%(villageNumber);
                    if(v.fitness > best.fitness)best = v;
                }
            }
            
            System.out.println("Villages: " + String.valueOf(villageNumber) + " Value: " + String.valueOf(best.value) + " Fitness: " + String.valueOf(best.fitness) + "(" + best.toString() + ")");
        }
        System.out.println();
    }
    
    
    
}
