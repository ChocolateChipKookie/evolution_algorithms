package hr.fer.zemris.optjava.dz8;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MOOP {

    public static void main(String[] args){

        Random rand = new Random();
        int populationSize = Integer.parseInt(args[1]);
        MOOPSolution[] solutions = new MOOPSolution[populationSize];

        MOOPProblem function = null;
        int f = Integer.parseInt(args[0]);
        int results;
        Boundaries[] boundaries = null;
        if(f == 1){
            function = new func_1();
            results = 4;
            boundaries = new Boundaries[function.getNumberOfObjectives()];
            for(int i = 0; i < function.getNumberOfObjectives(); ++i){
                boundaries[i] = new Boundaries(-5, 5);
            }
        }
        else{
            function = new func_2();
            results = 2;
            boundaries = new Boundaries[]{new Boundaries(0.1, 1), new Boundaries(0, 5)};
        }


        for(int i = 0; i < populationSize; ++i){
            solutions[i] = MOOPSolution.getRandomSolution(function.getNumberOfObjectives(), results, boundaries, rand);
        }
        double init = 0.3;
        double fin = 0.1;
        int iterations = Integer.parseInt(args[3]);
        MOOPMutate mutate = new MOOPMutate(init, 1/Math.pow((init/fin), 1./iterations), 0.5, boundaries, rand);
        MOOP moop = new MOOP(function, solutions, iterations, args[2].equals("decision-space"), mutate, rand);
        moop.run();
    }

    private boolean decisionSpace;
    private MOOPProblem function;
    private int maxIter;
    private Random rand;
    private MOOPSolution[] start;
    private double SHARE_FACTOR = 10;
    private double EPSILON = 0.5;
    private MOOPMutate mutate;

    public MOOP(MOOPProblem function, MOOPSolution[] start, int maxIter, boolean decisionSpace, MOOPMutate mutate, Random rand){
        this.function = function;
        this.decisionSpace = decisionSpace;
        this.maxIter = maxIter;
        this.rand = rand;
        this.start = start;
        this.mutate = mutate;
    }

    public void run(){

        MOOPSolution[] solutions = start;
        MOOPSolution[] newGeneration = new MOOPSolution[start.length];

        RouletteWheelSelection<MOOPSolution> selection = new RouletteWheelSelection<>(rand);

        MOOPCrossover crossover = new MOOPCrossover(rand);

        for(int iteration = 0; iteration < maxIter; ++iteration){

            for(MOOPSolution s : solutions){

                function.evaluateSolution(s.solution, s.values);
                s.dominatedBy = 0;
            }

            for (int i = 0; i < solutions.length; ++i) {
                if (solutions[i].front == -1) continue;
                for (int j = 0; j < solutions.length; ++j) {
                    if (!solutions[j].dominates.contains(i)) {
                        if (solutions[i].dominates(solutions[j])) {
                            solutions[i].addDominated(j);
                            ++solutions[j].dominatedBy;
                        }
                    }
                }
            }

            int front = 0;

            boolean done = false;
            while(!done){
                List<Integer> dec = new ArrayList<>();
                for (MOOPSolution solution : solutions) {
                    if (solution.dominatedBy == 0) {
                        solution.dominatedBy = -1;
                        solution.front = front;
                        dec.addAll(solution.dominates);
                    }
                }
                done = dec.isEmpty();
                ++front;

                for (Integer i : dec) {
                    --solutions[i].dominatedBy;
                }
            }

            List<MOOPSolution>[] fronts = new List[front];
            //Postavljanje fronti
            for(int i = 0; i < front; ++i){
                fronts[i] = new ArrayList<>();
            }

            for(MOOPSolution s :solutions){
                fronts[s.front].add(s);
            }

            if(iteration%10 == 0){
                for(int i = 0; i < front; ++i){
                    System.out.print((i + 1) + "{" + fronts[i].size() + "} ");
                }
                System.out.println();
            }

            if(decisionSpace){
                Boundaries[] minmax = new Boundaries[solutions[0].values.length];
                for(int i = 0; i < minmax.length; ++i){
                    minmax[i] = new Boundaries(Integer.MAX_VALUE, Integer.MIN_VALUE);
                }

                //Postavljanje minova i maksova
                for(MOOPSolution s : solutions){
                    for(int i = 0; i < s.values.length; ++i){
                        if(s.values[i] < minmax[i].lower){
                            minmax[i].lower = s.values[i];
                        }
                        if(s.values[i] > minmax[i].upper){
                            minmax[i].upper = s.values[i];
                        }
                    }
                }


                double fn = solutions.length;
                double minf = Double.MAX_VALUE;

                double share = 0;
                for(Boundaries mm : minmax){
                    mm.upper += Double.MIN_VALUE;
                    mm.lower -= Double.MIN_VALUE;
                    share += mm.upper - mm.lower;
                }
                share /= minmax.length;
                share /= SHARE_FACTOR;


                //Fitness function
                for(List<MOOPSolution> f : fronts){
                    for(int i = 0; i < f.size(); ++i){
                        int neighbours = 0;

                        for (MOOPSolution solution : f) {
                            if (f.get(i).valuesDistance(solution, minmax) < share) {
                                neighbours++;
                            }
                        }

                        f.get(i).fitness = fn/(neighbours);
                        if(f.get(i).fitness < minf)minf = f.get(i).fitness;
                    }
                    fn = minf*(1-EPSILON);
                }
            }
            else{
                Boundaries[] minmax = new Boundaries[solutions[0].solution.length];
                for(int i = 0; i < minmax.length; ++i){
                    minmax[i] = new Boundaries(Integer.MAX_VALUE, Integer.MIN_VALUE);
                }

                //Postavljanje minova i maksova
                for(MOOPSolution s : solutions){
                    for(int i = 0; i < s.solution.length; ++i){
                        if(s.solution[i] < minmax[i].lower){
                            minmax[i].lower = s.solution[i];
                        }
                        if(s.solution[i] > minmax[i].upper){
                            minmax[i].upper = s.solution[i];
                        }
                    }
                }


                double fn = solutions.length;
                double minf = Double.MAX_VALUE;

                double share = 0;
                for(Boundaries mm : minmax){
                    mm.upper+=Double.MIN_VALUE;
                    mm.lower-=Double.MIN_VALUE;
                    share += mm.upper - mm.lower;
                }
                share /= minmax.length;
                share /= SHARE_FACTOR;


                //Fitness function
                for(List<MOOPSolution> f : fronts){

                    for(int i = 0; i < f.size(); ++i){
                        int neighbours = 0;

                        for (MOOPSolution solution : f) {
                            if (f.get(i).solutionDistance(solution, minmax) < share) {
                                neighbours++;
                            }
                        }

                        f.get(i).fitness = fn/(neighbours);
                        if(f.get(i).fitness < minf)minf = f.get(i).fitness;
                    }
                    fn = minf*(1-EPSILON);
                }
            }

            if(iteration == maxIter - 1)break;
            MOOPSolution[] parents = new MOOPSolution[2];
            for(int i = 0; i < newGeneration.length; ++i){
                selection.select(solutions, parents);
                newGeneration[i] = crossover.crossover(parents[0], parents[1]);
                newGeneration[i] = mutate.mutate(newGeneration[i]);
            }
            mutate.updateSigme();
            solutions = newGeneration;
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("izlaz-obj.txt"))) {
            for(MOOPSolution s :solutions){
                writer.write("(");
                function.evaluateSolution(s.solution, s.values);
                for(int i = 0; i < s.values.length - 1; ++i){
                    writer.write(s.values[i] + ", ");
                }
                writer.write(s.values[s.values.length - 1] + ")");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(BufferedWriter writer = new BufferedWriter(new FileWriter("izlaz-dec.txt"))) {
            for(MOOPSolution s :solutions){
                writer.write("(");
                for(int i = 0; i < s.solution.length - 1; ++i){
                    writer.write(s.solution[i] + ", ");
                }
                writer.write(s.solution[s.solution.length - 1] + ")");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}