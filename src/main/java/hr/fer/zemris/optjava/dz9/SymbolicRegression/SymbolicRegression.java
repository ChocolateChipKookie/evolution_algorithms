package hr.fer.zemris.optjava.dz9.SymbolicRegression;

import hr.fer.zemris.optjava.dz9.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class SymbolicRegression {

    public static void main(String[] args){

        Random rand = new Random();

        Config c = new Config("config.txt");
        SymbolicRegressionFunction srf = new SymbolicRegressionFunction("9-datoteke/" + args[0]);

        SymbolicRegressionGPTree[] startPopulation = new SymbolicRegressionGPTree[c.populationSize];
        int each = c.populationSize/((c.maxStartDepth - 1)*2);  //83

        for(int i = 0; i + 2 <= c.maxStartDepth; ++i){
            for(int j = 0; j < each; ++j){
                startPopulation[i*2 * each + j] = new SymbolicRegressionGPTree(c.functionNodes, c.constantRange, srf.numberOfInputs(), i + 2, false, rand);
            }
            for(int j = 0; j < each; ++j){
                startPopulation[(i*2+1) * each + j] = new SymbolicRegressionGPTree(c.functionNodes, c.constantRange, srf.numberOfInputs(), i + 2, true, rand);
            }
        }

        for(int i = (c.maxStartDepth - 1) * each*2; i < c.populationSize; ++i){
            startPopulation[i] = new SymbolicRegressionGPTree(c.functionNodes, c.constantRange, srf.numberOfInputs(), 2 + rand.nextInt(c.maxStartDepth - 1), rand.nextBoolean(), rand);
        }

        SubtreeSwitchMutation<Double, Double> m = new SubtreeSwitchMutation<>(c.maxDepth, c.maxSize, 2,  rand);
        TreeCrossover<Double, Double> cr = new TreeCrossover<>(c.maxDepth, c.maxSize, rand);
        Percentages p = new Percentages(c.crossover, c.reproduction, c.mutation);
        Tournament<SymbolicRegressionGPTree> t = new Tournament<>(c.tournamentSize, rand);


        GeneticAlgorithm<Double, Double> ga = new GeneticAlgorithm(m, cr, p, t, c.iterations, srf, startPopulation, 0.01, rand);
        ga.run();

    }

}
