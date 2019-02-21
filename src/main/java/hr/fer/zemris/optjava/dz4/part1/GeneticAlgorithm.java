/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz4.part1;

import hr.fer.zemris.optjava.algorithms.genetic.IMutate;
import hr.fer.zemris.optjava.algorithms.genetic.ICrossover;
import hr.fer.zemris.optjava.algorithms.genetic.ISelection;
import hr.fer.zemris.optjava.dz3.Function;
import hr.fer.zemris.optjava.algorithms.IFunction;
import hr.fer.zemris.optjava.algorithms.IOptAlgorithm;
import hr.fer.zemris.optjava.solutions.doublearray.DoubleArraySolution;
import hr.fer.zemris.optjava.solutions.doublearray.PassThroughDecoder;
import hr.fer.zemris.optjava.solutions.IDecoder;
import hr.fer.zemris.optjava.solutions.INeighbourhood;
import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Adi
 * @param <T>
 */
public class GeneticAlgorithm<T extends SingleObjectiveSolution> implements IOptAlgorithm{
    
    public static void main(String[] args){
        Random rand = new Random();
        IOptAlgorithm alg = null;
       
        int n = Integer.parseInt(args[0]);
        double limit = Double.parseDouble(args[1]);
        int maxGenerations = Integer.parseInt(args[2]);
        double sigma = Double.parseDouble(args[4]);

        ISelection selection = null;
        ICrossover crossover = new BLXAlpha(rand);
        
        //Constants:
        //MutationFactor
        //Sigma
        //Limit
        //Population size
        
        double mutationFactor = 0.1;
        IMutate mutate = new SelectiveMutate(sigma, mutationFactor, rand);
        //IMutate mutate = new Mutate(0.1, rand);
        
        if(args[3].equals("rouletteWheel")){
            double survivalPercentage = 0.2;
            //selection = new RouletteWheel(rand, crossover, mutate);
            selection = new ElitistRouletteWheel(rand, crossover, mutate, survivalPercentage);
        }else{
            selection = new Tournament(Integer.parseInt(args[3].substring(11)),rand, crossover, mutate);
        }
        
        IDecoder decoder = new PassThroughDecoder();

        DoubleArraySolution tmp = new DoubleArraySolution(6);
        DoubleArraySolution[] startWith = new DoubleArraySolution[n];
        for(int i = 0; i < n; ++i){
            tmp.randomize(rand, new double[]{-10, -10, -10, -10, -10, -10}, new double[]{10, 10, 10, 10, 10, 10});
            startWith[i] = tmp.newLikeThis();
        }
        
        BufferedReader br = null;
        double[][] params = new double[20][];
        int index = 0;
        
        try{

            br = new BufferedReader(new FileReader("02-zad-prijenosna.txt"));
            String s;
            while((s = br.readLine())!= null){                
                if(s.charAt(0)== '#')continue;
                
                String[] parameters = s.substring(1, s.length()-1).split(", ");
                double[] param = new double[parameters.length];
                for(int i = 0; i<parameters.length; ++i){
                    param[i] = Double.parseDouble(parameters[i]);
                }
                params[index++] = param;
                if(index == 20)break;                
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally{
            try {
                if (br != null) br.close();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
        

        IFunction func = new Function(params);
        alg = new GeneticAlgorithm(decoder, startWith, func, selection, rand, maxGenerations, limit);
        
        alg.run();
    }

    private final IDecoder<T> decoder;
    private final T[] startWith;
    private final IFunction function;
    private final Random rand;
    private final ISelection selection;
    private final int iterations;
    private final double limit;
    private final int PRINT_COUNT = 2000;
    
    public GeneticAlgorithm(IDecoder<T> decoder, T[] startWith, IFunction function, ISelection selection, Random rand, int iterations, double limit){
        this.decoder = decoder;
        this.startWith = startWith;
        this.function = function;
        this.selection = selection;
        this.rand = rand;
        this.iterations = iterations;
        this.limit = limit;
    }
    
    @Override
    public void run() {
        T[] currentGeneration = startWith;
        int printCount = 0;
        int bestIndex = 0;
        for(int j = 0; j<iterations; ++j){

            for(int i = 0; i < currentGeneration.length; ++i){
                //Tako da se ne provjeravaju vec postavljene vrijednosti koda tournamenta
                if( currentGeneration[i].value < limit / 10){
                    currentGeneration[i].value = function.valueAt(decoder.decode(currentGeneration[i]));
                    currentGeneration[i].fitness = 1000./(currentGeneration[i].value + 1);
                }
            }
            
            bestIndex = 0;
            double maxFitness = 0;
            for(int i = 0; i<currentGeneration.length; ++i){
                if(currentGeneration[i].fitness > maxFitness){
                    bestIndex = i;
                    maxFitness = currentGeneration[i].fitness;
                }
            }
            
            if(((printCount++)%PRINT_COUNT) == 0){
                System.out.print(String.valueOf(printCount - 1) + ". Parameters: ");
                for(double parameter : decoder.decode(currentGeneration[bestIndex])){
                    System.out.print( String.format("%.4f", parameter) + " ");
                }
                System.out.print("Fitness: " + currentGeneration[bestIndex].fitness);
                System.out.println(" Value: " + currentGeneration[bestIndex].value);
            }


            if(currentGeneration[bestIndex].value < limit){
                break;
            }            
            
            currentGeneration = (T[]) selection.select(currentGeneration);
        }
        
        System.out.print("Parameters: ");
        for(double parameter : decoder.decode(currentGeneration[bestIndex])){
            System.out.print(parameter + " ");
        }
        System.out.print("Fitness: " + currentGeneration[bestIndex].fitness);
        System.out.println(" Value: " + currentGeneration[bestIndex].value);
    }
}
