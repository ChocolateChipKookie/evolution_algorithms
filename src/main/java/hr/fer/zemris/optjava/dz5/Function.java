/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz5;

import hr.fer.zemris.optjava.algorithms.IFunction;

/**
 *
 * @author Adi
 */
public class Function{
    
    private Map map;
    
    public Function(Map map){
        this.map = map;
    }
    
    public void setValue(PermutationSolution solution){
        double sum = 0;
        for(int i = 0; i < solution.permutation.length - 1; ++i){
            sum += map.getDistance(solution.permutation[i], solution.permutation[i + 1]);
        }
        solution.setValue( sum + map.getDistance(solution.permutation[0], solution.permutation[solution.permutation.length - 1]) );
    }   
}
