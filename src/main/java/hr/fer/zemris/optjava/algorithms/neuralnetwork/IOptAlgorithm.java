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
public interface IOptAlgorithm<T extends SingleObjectiveSolution> {
    
    T run();
    T getSolution();
    double[] getSolutionVector();
}
