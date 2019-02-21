/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz4.part2;

import hr.fer.zemris.optjava.algorithms.genetic.ICrossover;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class PositionVectorCrossover implements ICrossover<PositionVectorSolution>{
    private Random rand;
    
    public PositionVectorCrossover(Random rand){
        this.rand = rand;
    }
    
    @Override
    public PositionVectorSolution CreateOffspring(PositionVectorSolution mother, PositionVectorSolution father) {
        LinkedList<Integer> possibleLocations = new LinkedList<>();
        PositionVectorSolution res = new PositionVectorSolution(mother.positions.length, mother.rand);
        
        
        for(int i = 0; i < mother.positions.length; ++i){
            possibleLocations.add(i);
        }
        
        for(int i = 0; i < mother.positions.length; ++i){
            int location = 0;
            if(rand.nextBoolean()){
                location = mother.positions[i];
                
                if(!possibleLocations.contains((Integer) location)){
                    location = father.positions[i];
                    
                    if(!possibleLocations.contains((Integer) location)){
                        location = -1;
                    }
                }
            }
            else{
                location = father.positions[i];
                
                if(!possibleLocations.contains((Integer) location)){
                    location = mother.positions[i];
                    
                    if(!possibleLocations.contains((Integer) location)){
                        location = -1;
                    }
                }
            }
            if(location != -1)possibleLocations.remove((Integer) location);
            res.positions[i] = location;
        }

        for(int i = 0; i < mother.positions.length; ++i){
            if(res.positions[i] == -1){
                int location = possibleLocations.get(rand.nextInt(possibleLocations.size()));
                possibleLocations.remove((Integer)location);
                res.positions[i] = location;
            }
        }        
        
        return res;
    }
    
}
