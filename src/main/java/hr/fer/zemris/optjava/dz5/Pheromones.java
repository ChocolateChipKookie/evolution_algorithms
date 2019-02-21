/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz5;

/**
 *
 * @author Adi
 */
public final class Pheromones {
    public final int cities;
    private double[][] pheromones;
    private double tmax;
    private double tmin;
    private double rho;
    private double minDistance;
    private double a;
    
    
    public Pheromones(int cities, double minDistance, double rho, double a){
        this.cities = cities;
        pheromones = new double[cities][cities];
        this.minDistance = minDistance;
        this.a = a;
        this.tmax = 1/rho/minDistance;
        this.tmin = tmax / a;
        this.rho = rho;
        reset();
    }
    
    public void reset(){
        for(int i = 0; i < cities; ++i){
            for(int j = 0; j < cities; ++j){
                pheromones[i][j] = tmax;
            }
        }
    }
    
    public void updateMinDistance(double minDistance){
        if(minDistance > this.minDistance) return;
        this.minDistance = minDistance;
        tmax = 1/(rho*minDistance);
        tmin = tmax / a;
        
        for(int i = 0; i < cities; ++i){
            for(int j = 0; j < cities; ++j){
                if(pheromones[i][j] > tmax) pheromones[i][j] = tmax;
            }
        }
    }
    
    public void setTrack(PermutationSolution solution){
        double pheromoneValue = 1/solution.getValue();
        for(int i = 0; i < cities; ++i){
            int j = (i + 1)%cities;
            if(pheromones[solution.permutation[i]][solution.permutation[j]] + pheromoneValue > tmax){
                pheromones[solution.permutation[i]][solution.permutation[j]] = 
                        pheromones[solution.permutation[j]][solution.permutation[i]] = tmax;
            }
            else{
                pheromones[solution.permutation[i]][solution.permutation[j]] = 
                        pheromones[solution.permutation[j]][solution.permutation[i]] += tmax;
            }
        }
    }
    
    public void evaporate(){
        for(int i = 0; i < cities; ++i){
            for(int j = 0; j < cities; ++j){
                if ((pheromones[i][j] *= (1 - rho)) < tmin) pheromones[i][j] = tmin;
            }
        }
    }
    
    public double getPheromone(int i1, int i2){
        return pheromones[i1][i2];
    }
}
