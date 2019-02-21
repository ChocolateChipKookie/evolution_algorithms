/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Adi
 */
public class AntColony {
    
    public static void main(String[] args){
        
        Map map = new Map(args[0] + ".tsp");
        int k = Integer.parseInt(args[1]);
        int l = Integer.parseInt(args[2]);
        int maxIter = Integer.parseInt(args[3]);
        
        //6_inputs\bays29 4 20 20000
        //
        //
        //
        
        
        double n = map.cities();
        double p = 0.95;
        double a = n * ( n - 1 )/(n* (-( 1 - Math.pow(p, - 1/n))));
        
        double rho = 0.1;
        double alpha = 2;
        double beta = 0.2;
        
        Function func = new Function(map);
        
        AntColony.run(func, map, k, l, maxIter, a, rho, alpha, beta);
        
        try(BufferedReader br = new BufferedReader(new FileReader(args[0] + ".opt.tour"));){
            String s;

            while(!(s = br.readLine()).contains("TOUR_SECTION"));
            
            PermutationSolution solution = new PermutationSolution(map.cities());
            for(int i = 0; i < map.cities(); ++i){
                solution.permutation[i] = Integer.parseInt(br.readLine()) - 1;
            }
            
            func.setValue(solution);
            
            System.out.println(String.valueOf(solution.getValue()) + " " + solution.printFrom(0));
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        
    }
    
    
    static public void run(Function func, Map map, int closestCities, int ants, int iterations, double a, double rho, double alpha, double beta){
        List<List<Integer>> neighbours = new ArrayList<>();
        Random rand = new Random();
        
        List<Integer> allCities = new ArrayList<>();
        for(int i = 0; i < map.cities(); ++i){
            allCities.add(i);
        }

        double sum = 0;
        int currentCity = 0;
        allCities.remove(0);
        
        while(!allCities.isEmpty()){
            
            int closest = 0;
            double distance = Double.MAX_VALUE;
            double tmp;
            for(int i = 0, k = allCities.size(); i < k; ++i){
                if( (tmp = map.getDistance(currentCity, allCities.get(i))) < distance){
                    distance = tmp;
                    closest = allCities.get(i);
                }
            }
            
            sum += distance;
            allCities.remove((Integer) closest);
            currentCity = closest;
        }
        
        Pheromones pheromones = new Pheromones(map.cities(), sum, rho, a);
        
        for(int i = 0; i < map.cities(); ++i){
            neighbours.add(map.neighbours(i, closestCities));
        }
        
        PermutationSolution bestSoFar = new PermutationSolution(0);
        bestSoFar.setValue(Double.MAX_VALUE);
        
        double itbChance = 1;
        
        double itbChanceFactor = Math.pow((5./Math.pow(map.cities(), 1./2)), 1./iterations);
        int stagnationCounter = 0;
        
        for(int i = 0; i < map.cities(); ++i){
            allCities.add(i);
        }
        
        for(int i = 0; i < iterations; ++i){
            PermutationSolution iterationBest = new PermutationSolution(0);
            iterationBest.setValue(Double.MAX_VALUE); 
            
            for(int m = 0; m < ants; ++m){
            
                List<Integer> cities = new ArrayList<>(allCities);

                currentCity = rand.nextInt(map.cities());
                int firstCity = currentCity;
                cities.remove((Integer)firstCity);

                PermutationSolution currentSolution = new PermutationSolution(map.cities());
                currentSolution.permutation[0] = currentCity;
                for(int j = 1, k = map.cities(); j<k; ++j){
                    List<Integer> nextCity = new ArrayList(neighbours.get(currentCity));
                    nextCity.retainAll(cities);
                    if(nextCity.isEmpty()){
                        nextCity.addAll(cities);
                    }

                    double distancesSum = 0;
                    for(Integer city : nextCity){
                        distancesSum += Math.pow(pheromones.getPheromone(currentCity, city), alpha) 
                                * Math.pow(map.getDistance(currentCity, city), beta);
                    }

                    double percentage = rand.nextDouble();
                    for(Integer city : nextCity){
                        double currentValue = Math.pow(pheromones.getPheromone(currentCity, city), alpha) 
                                * Math.pow(map.getDistance(currentCity, city), beta);
                        percentage -= currentValue/distancesSum;

                        if(percentage <0){
                            currentSolution.permutation[j] = city;
                            currentCity = city;
                            cities.remove(city);
                            break;
                        }
                    }
                }
                func.setValue(currentSolution);
                if(currentSolution.getValue() < iterationBest.getValue()){
                    iterationBest = currentSolution;
                }
            }
            
            if(bestSoFar.getValue() > iterationBest.getValue()){
                bestSoFar = iterationBest;
                pheromones.updateMinDistance(iterationBest.getValue());
                
                System.out.println(String.valueOf(bestSoFar.getValue()) + " " + bestSoFar.printFrom(0));
                
                stagnationCounter = 0;
            }

            if(stagnationCounter++ > iterations/20){
                pheromones.reset();
                stagnationCounter = 0;
            }
            
            pheromones.evaporate();
            
            itbChance *= itbChanceFactor;
            if(itbChance < rand.nextDouble()){
                pheromones.setTrack(bestSoFar);
            }
            else {
                pheromones.setTrack(iterationBest);
            }
        }
        System.out.println("fin");
    }
}
