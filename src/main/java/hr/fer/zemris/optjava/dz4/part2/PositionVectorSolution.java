/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz4.part2;

import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class PositionVectorSolution extends SingleObjectiveSolution{
    //Pozicija tvornica
    public int[] positions;
    public Random rand;
    
  
    public PositionVectorSolution(int locations, Random rand){
        positions = new int[locations];
        this.rand = rand;
    }
    
    public void randomise(){
        for(int i = 0; i < positions.length; ++i){
            positions[i] = i;
        }
        
        for(int i = 0; i < positions.length; ++i){
            int tmp = rand.nextInt(positions.length);
            int tmp_ = positions[i];
            positions[i] = positions[tmp];
            positions[tmp] = tmp_;
        }
    }
    
    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append(positions[0]);
        for(int i = 1; i< positions.length; ++i){
            b.append(" ");
            b.append(positions[i]);
        }
        return b.toString();
    }
    
}
