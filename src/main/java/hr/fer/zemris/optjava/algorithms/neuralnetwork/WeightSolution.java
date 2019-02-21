/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.algorithms.neuralnetwork;

import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;

/**
 *
 * @author Adi
 */
public class WeightSolution extends SingleObjectiveSolution {
    public double[] weights;
    
    public WeightSolution(int weights){
        this.weights = new double[weights];
    }

    public WeightSolution clone(){
        WeightSolution solution = new WeightSolution(0);
        solution.weights = this.weights.clone();
        solution.value = this.value;
        return solution;
    }
}
