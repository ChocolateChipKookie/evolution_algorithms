package hr.fer.zemris.optjava.dz7;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.IFunction;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.IOptAlgorithm;
import hr.fer.zemris.optjava.algorithms.neuralnetwork.WeightSolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DiffEvo implements IOptAlgorithm<WeightSolution> {

    private WeightSolution finalSolution;
    private int iterations;
    private int populationSize;
    private double alpha;
    private double beta;
    private double maxError;
    private Function function;
    private double min, max;
    private Random rand;

    public DiffEvo(Function function, int iterations, int populationSize, double min, double max, double alpha, double beta, double maxError){
        this.function = function;
        this.iterations = iterations;
        this.alpha = alpha;
        this.beta = beta;
        this.populationSize = populationSize;
        this.maxError = maxError;
        this.min = min;
        this.max = max;
        this.rand = new Random();
        this.finalSolution = new WeightSolution(0);
        finalSolution.value = Double.MAX_VALUE;
    }

    @Override
    public WeightSolution run() {
        WeightSolution[] solutions = new WeightSolution[populationSize];
        for(int k = 0; k < solutions.length; ++k){
            solutions[k] = new WeightSolution(function.neuralNetwork.getWeightVectorLength());
            for(int i = 0; i < solutions[k].weights.length; ++i){
                solutions[k].weights[i] = min + (max - min) * rand.nextDouble();
            }
            solutions[k].value = function.valueAt(solutions[k].weights);
            if(solutions[k].value < finalSolution.value){
                finalSolution = solutions[k].clone();
            }
        }

        ArrayList<Integer> mutantSeed = new ArrayList<>();
        for(int i = 0; i < solutions.length; ++i){
            mutantSeed.add(i);
        }

        for(int iters = 0; iters < iterations && finalSolution.value > maxError; ++iters){
            if(iters % 100 == 0)System.out.println(finalSolution.value);
            for(int g = 0; g < solutions.length; ++g){
                Collections.shuffle(mutantSeed);

                //rand (best-rand) (rand-rand)
                WeightSolution base = solutions[mutantSeed.get(0)].clone();
                WeightSolution best = finalSolution.clone();
                WeightSolution r1 = solutions[mutantSeed.get(1)].clone();
                WeightSolution r2 = solutions[mutantSeed.get(2)].clone();
                WeightSolution r3 = solutions[mutantSeed.get(3)].clone();

                for(int i = 0; i < base.weights.length; ++i){
                    base.weights[i] += rand.nextDouble()*beta*(r1.weights[i] - r2.weights[i]) + rand.nextDouble()*beta*(best.weights[i] - r3.weights[i]);
                }

                for(int i = 0; i < base.weights.length; ++i){
                    if(rand.nextDouble() > alpha){
                        base.weights[i] = solutions[g].weights[i];
                    }
                }

                base.value = function.valueAt(base.weights);

                if(base.value < solutions[g].value) {
                    solutions[g] = base;
                    if (base.value < finalSolution.value) {
                        finalSolution = base.clone();
                    }
                }
            }
        }

        return finalSolution.clone();
    }

    @Override
    public WeightSolution getSolution() {
        return finalSolution;
    }

    @Override
    public double[] getSolutionVector() {
        return finalSolution.weights;
    }
}
