package hr.fer.zemris.optjava.dz11.genetic_algorithm;

import hr.fer.zemris.optjava.dz11.genetic_algorithm.crossovers.RectangleCrossover;
import hr.fer.zemris.optjava.dz11.gray_scale_image.Bounds;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.mutations.RectangleMutation;
import hr.fer.zemris.optjava.dz11.drawing_window.DrawFrame;
import hr.fer.zemris.optjava.dz11.task.*;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class GeneticAlgorithm {

    private RectangleMutation mutation;
    private RectangleCrossover crossover;
    private Tournament tournament;
    private int iterations;
    private Evaluator evaluator;
    private RectangleGenome[] population;
    public  RectangleGenome best = null;
    private double goalFitness;
    private int threads = Runtime.getRuntime().availableProcessors();
    private BlockingQueue<Task> tasks;
    private BlockingQueue<Result> results;
    private static double BEST_INDIVIDUALS = 0.05;
    private static double COPYCATS = 0.2;
    private DrawFrame drawFrame;
    private Bounds bounds;
    private int newRectangle;


    public GeneticAlgorithm(RectangleMutation mutation, RectangleCrossover crossover, Tournament tournament, int iterations, Evaluator evaluator, RectangleGenome[] start, double goalFitness, Bounds bounds, DrawFrame drawFrame){
        this.mutation = mutation;
        this.crossover = crossover;
        this.tournament = tournament;
        this.iterations = iterations;
        this.evaluator = evaluator;
        this.population = start;
        this.goalFitness = goalFitness;
        this.drawFrame = drawFrame;
        this.bounds = bounds;
        this.newRectangle = iterations/start[0].rectangles;

        tasks = new LinkedBlockingDeque<>();
        results = new LinkedBlockingDeque<>();

        for(int i = 0; i < threads; ++i){
            (new WorkerThread(tasks, results)).start();
        }
    }

    public RectangleGenome run() {
        RectangleGenome[] generation = new RectangleGenome[population.length];

        for(int iteration = 0; iteration < iterations; ++iteration) {

            if(iteration % newRectangle == 0){
                if(!population[0].hasMaxRectangles()){
                    for(RectangleGenome r : population){
                        r.incCurrentRectangles();
                        r.setRandom(r.currentRectangles - 1, bounds);
                    }
                }
            }

            evaluate(population);
            Arrays.sort(population, Comparator.comparingDouble(t -> -t.fitness));

            RectangleGenome currentBest = population[0];

            if(best == null || best.fitness < currentBest.fitness){
                best = currentBest;
                if(best.fitness > goalFitness)break;
                System.out.println(iteration + ". Best: " + currentBest.fitness);
                drawFrame.drawGenome(best);
                drawFrame.drawScore((int)-best.fitness);
            }

            int best = (int)(population.length * BEST_INDIVIDUALS);
            System.arraycopy(population, 0, generation, 0, best);

            for(int i = best; i < generation.length; ++i){
                RectangleGenome[] parents = new RectangleGenome[2];
                tournament.select(population, parents);
                generation[i] = crossover.crossover(parents[0], parents[1]);
                generation[i] = mutation.mutate(generation[i]);
            }

            if((iteration + 1) % newRectangle == 0){
                int copycats = (int)(population.length * COPYCATS);

                for(int i = 1; i <= copycats; ++i){
                    generation[generation.length - i] = generation[0].clone();
                }
            }

            drawFrame.drawIteration(iteration);
            population = generation;
        }
        terminateThreads();
        drawFrame.drawIteration(-1);
        return best;
    }

    private void evaluate(RectangleGenome[] generation){
        List<RectangleGenome>[] lists = new List[threads];

        for(int i = 0; i < threads; ++i) lists[i] = new ArrayList<>();

        int pos = 0;
        for(RectangleGenome rg : generation){
            lists[pos].add(rg);
            pos = (pos + 1)%threads;
        }

        try {
            for(int i = 0; i < threads; ++i)
                tasks.put(new EvaluationTask(evaluator, lists[i]));
            for(int i = 0; i < threads; ++i)
                results.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void terminateThreads(){
        for(int i = 0; i < threads; ++i) {
            try {
                tasks.put(Task.poison);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
