package hr.fer.zemris.optjava.dz11.genetic_algorithm;

import hr.fer.zemris.optjava.dz11.genetic_algorithm.crossovers.RectangleCrossover;
import hr.fer.zemris.optjava.dz11.gray_scale_image.Bounds;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.mutations.RectangleMutation;
import hr.fer.zemris.optjava.dz11.drawing_window.DrawFrame;
import hr.fer.zemris.optjava.dz11.task.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class MultiThreadedGA {

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
    private static double COPYCATS = 1;
    private DrawFrame drawFrame;
    private Bounds bounds;
    private int newRectangle;


    public MultiThreadedGA(RectangleMutation mutation, RectangleCrossover crossover, Tournament tournament, int iterations, Evaluator evaluator, RectangleGenome[] start, double goalFitness, Bounds bounds, DrawFrame drawFrame){
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
                //System.out.println(iteration + ". Best: " + currentBest.fitness);
                drawFrame.drawGenome(currentBest);
                drawFrame.drawScore((int)-best.fitness);
            }

            int best = (int)(population.length * BEST_INDIVIDUALS);
            System.arraycopy(population, 0, generation, 0, best);

            generation = crossover(population);
            generation = mutate(generation);

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

    private RectangleGenome[] crossover(RectangleGenome[] population){
        List<RectangleGenome> result = new ArrayList<>();

        try {
            int perThread = population.length / threads;
            for(int i = 0; i < threads - 1; ++i)
                tasks.put(new CrossoverTask(population, crossover, tournament, perThread));
            tasks.put(new CrossoverTask(population, crossover, tournament, perThread + population.length % threads));

            for(int i = 0; i < threads; ++i)
                result.addAll(results.take().results);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result.toArray(new RectangleGenome[result.size()]);
    }

    private RectangleGenome[] mutate(RectangleGenome[] population){
        List<RectangleGenome>[] lists = new List[threads];
        List<RectangleGenome> result = new ArrayList<>();

        for(int i = 0; i < threads; ++i) lists[i] = new ArrayList<>();

        int pos = 0;
        for(RectangleGenome rg : population){
            lists[pos].add(rg);
            pos = (pos + 1)%threads;
        }

        try {
            for(int i = 0; i < threads; ++i)
                tasks.put(new MutationTask(lists[i], mutation));

            for(int i = 0; i < threads; ++i)
                result.addAll(results.take().results);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result.toArray(new RectangleGenome[result.size()]);
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
