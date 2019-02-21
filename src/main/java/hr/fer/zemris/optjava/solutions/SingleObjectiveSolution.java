/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.solutions;

/**
 *
 * @author Adi
 */
public class SingleObjectiveSolution implements Comparable{
    public double fitness;
    public double value;
    
    @Override
    public int compareTo(Object other){
        SingleObjectiveSolution solution = (SingleObjectiveSolution) other;
        
        if(this.fitness > solution.fitness)return 1;
        else if(this.fitness < solution.fitness)return -1;
        return 0;
    }
}
