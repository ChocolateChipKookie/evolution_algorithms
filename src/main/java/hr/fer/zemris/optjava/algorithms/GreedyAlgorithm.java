/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.algorithms;

import hr.fer.zemris.optjava.solutions.IDecoder;
import hr.fer.zemris.optjava.solutions.INeighbourhood;
import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;

/**
 *
 * @author Adi
 * @param <T>
 */
public class GreedyAlgorithm<T extends SingleObjectiveSolution> implements IOptAlgorithm{
    private IDecoder<T> decoder;
    private INeighbourhood<T> neighbourhood;
    private T startWith;
    private IFunction function;
    private boolean minimise;
    private int innerLoop;
    private int outerLoop;
    
    public GreedyAlgorithm(IDecoder<T> decoder, INeighbourhood<T> neighbourhood, T startWith, IFunction function, boolean minimise, int innerLoop, int outerLoop){
        this.decoder = decoder;
        this.neighbourhood = neighbourhood;
        this.startWith = startWith;
        this.function = function;
        this.minimise = minimise;
        this.innerLoop = innerLoop;
        this.outerLoop = outerLoop;
    }
    
    @Override
    public void run(){
        T solution = startWith;
        solution.value = function.valueAt(decoder.decode(solution));
        
        for(int j = 0; j < outerLoop; ++j){
            
            for(int i = 0; i < innerLoop; ++i){
                T newSolution = neighbourhood.randomNeighbour(solution);
                newSolution.value = function.valueAt(decoder.decode(newSolution));

                if(newSolution.value < solution.value)solution = minimise ? newSolution : solution;
                else solution = !minimise ? newSolution : solution;
            }
            System.out.print("Parameters: ");
            for(double parameter : decoder.decode(solution)){
                System.out.print(parameter + " ");
            }
            System.out.println("Value: " + solution.value);
        }
    }
}
