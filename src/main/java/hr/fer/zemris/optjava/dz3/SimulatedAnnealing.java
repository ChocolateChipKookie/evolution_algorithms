/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz3;

import hr.fer.zemris.optjava.algorithms.IFunction;
import hr.fer.zemris.optjava.algorithms.ITempSchedule;
import hr.fer.zemris.optjava.algorithms.IOptAlgorithm;
import hr.fer.zemris.optjava.algorithms.IOptAlgorithm;
import hr.fer.zemris.optjava.algorithms.ITempSchedule;
import hr.fer.zemris.optjava.solutions.IDecoder;
import hr.fer.zemris.optjava.solutions.INeighbourhood;
import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;
import java.util.Random;

/**
 *
 * @author Adi
 * @param <T> Solution type
 */
public class SimulatedAnnealing<T extends SingleObjectiveSolution> implements IOptAlgorithm {
    private final IDecoder<T> decoder;
    private final INeighbourhood<T> neighbourhood;
    private final T startWith;
    private final IFunction function;
    private final boolean minimise;
    private final ITempSchedule temp;
    private final Random rand;
    
    public SimulatedAnnealing(IDecoder<T> decoder, INeighbourhood<T> neighbourhood, T startWith, IFunction function, ITempSchedule temp, boolean minimise, Random rand){
        this.decoder = decoder;
        this.neighbourhood = neighbourhood;
        this.startWith = startWith;
        this.function = function;
        this.minimise = minimise;
        this.temp = temp;
        this.rand = rand;
    }
    
    @Override
    public void run(){
        T solution = startWith;
        solution.value = function.valueAt(decoder.decode(solution));
        
        for(int j = 0; j < temp.getOuterLoopCounter(); ++j){
            double temperature = temp.getNextTemperature();
            
            for(int i = 0; i < temp.getInnerLoopCounter(); ++i){
                T newSolution = neighbourhood.randomNeighbour(solution);
                newSolution.value = function.valueAt(decoder.decode(newSolution));

                if(minimise){
                    double energyDiff = newSolution.value - solution.value;
                    if(energyDiff < 0){
                        solution = newSolution;
                    }
                    else if(rand.nextDouble() < Math.exp(-energyDiff/temperature)){
                        solution = newSolution;
                    }
                }
                else{
                    double energyDiff = newSolution.value - solution.value;
                    if(energyDiff > 0){
                        solution = newSolution;
                    }
                    else if(rand.nextDouble() < Math.exp(energyDiff/temperature)){
                        solution = newSolution;
                    }
                }
            }
            System.out.print("Parameters: ");
            for(double parameter : decoder.decode(solution)){
                System.out.print(parameter + " ");
            }
            System.out.println("Value: " + solution.value);
        }
    }
    
}
