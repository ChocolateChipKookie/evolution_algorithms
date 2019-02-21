package hr.fer.zemris.optjava.dz9;

import hr.fer.zemris.optjava.dz9.SymbolicRegression.SymbolicRegressionFunction;

import java.util.*;

public class GeneticAlgorithm<O, I> {

    private SubtreeSwitchMutation<O, I> mutation;
    private TreeCrossover<O, I> crossover;
    private Tournament<GPTree<O, I>> tournament;
    private int iterations;
    private Function<GPTree<O, I>> function;
    private GPTree<O, I>[] population;
    private Random rand;
    private Percentages p;
    public  GPTree<O, I> best = null;
    private double goalFitness;


    public GeneticAlgorithm(SubtreeSwitchMutation<O, I> mutation, TreeCrossover<O, I> crossover, Percentages p,  Tournament<GPTree<O, I>> tournament, int iterations, Function<GPTree<O, I>> function, GPTree<O, I>[] start, double goalFitness, Random rand){
        this.mutation = mutation;
        this.crossover = crossover;
        this.tournament = tournament;
        this.iterations = iterations;
        this.function = function;
        this.population = start;
        this.rand = rand;
        this.p = p;
        this.goalFitness = goalFitness;
    }


    public GPTree<O, I> run() {
        GPTree<O, I>[] nextGeneration = new GPTree[population.length];
        evaluate(population);

        for(int iteration = 0; iteration < iterations; ++iteration) {

            if(best!= null)nextGeneration[0] = best;
            else nextGeneration[0] = getNext();
            for(int i = 1; i < nextGeneration.length; ++i){
                nextGeneration[i] = getNext();
            }

            evaluate(nextGeneration);
            Arrays.sort(nextGeneration, Comparator.comparingDouble(t -> t.fitness));

            GPTree<O, I> currentBest = nextGeneration[0];

            if(best == null || best.fitness > currentBest.fitness){
                best = currentBest;
                currentBest.getRoot().calculateHeight();
                currentBest.getRoot().calculateSubtreeSize();
                if(best.fitness< goalFitness)break;
                System.out.println("Best: " + currentBest.fitness + " (Depth: " + currentBest.getRoot().getHeight() + ", Size: " + currentBest.getRoot().getSubtreeSize() + ") " + currentBest.toString());
            }

            population = nextGeneration;
        }

        System.out.println(best.toString());
        return best;
    }

    private GPTree<O, I> getNext(){
        double n = rand.nextDouble();
        n -= p.mutation;
        if(n < 0){
            return mutation.mutate(tournament.select(population));
        }
        n -= p.crossover;
        if(n < 0){
            GPTree<O, I>[] parents = new GPTree[]{tournament.select(population), tournament.select(population)};
            return crossover.crossover(parents);
        }

        return tournament.select(population).clone();
    }

    private void evaluate(GPTree<O, I>[] population){
        for(GPTree<O, I> member : population){
            function.evaluate(member);
        }
    }
}
