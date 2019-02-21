package hr.fer.zemris.optjava.dz10;

import hr.fer.zemris.optjava.dz8.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class NSGA2 {

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
        int iterations = Integer.parseInt(args[2]);
        MOOPMutate mutate = new MOOPMutate(init, 1/Math.pow((init/fin), 1./iterations), 0.5, boundaries, rand);
        NSGA2 nsga2 = new NSGA2(function, solutions, iterations, mutate, rand);
        nsga2.run();
    }


    private MOOPProblem function;
    private int maxIter;
    private Random rand;
    private MOOPSolution[] start;
    private MOOPMutate mutate;

    public NSGA2(MOOPProblem function, MOOPSolution[] start, int maxIter, MOOPMutate mutate, Random rand){
        this.function = function;
        this.maxIter = maxIter;
        this.rand = rand;
        this.start = start;
        this.mutate = mutate;
    }

    public void run(){

        List<MOOPSolution> solutions = new ArrayList<>(Arrays.asList(start));
        Tournament<MOOPSolution> selection = new Tournament<>(5);

        int solutionSize = start.length;

        MOOPCrossover crossover = new MOOPCrossover(rand);

        for(int iteration = 0; iteration < maxIter; ++iteration){

            //Dodati novu djecu
            //Izracunati fronte od svih njih

            //Izracunavanje rjesenja
            for(MOOPSolution s : solutions){
                function.evaluateSolution(s.solution, s.values);
                s.dominatedBy = 0;
                s.dominates.clear();
                s.front = 0;
            }
            //Racunanje dominacija
            for (int i = 0; i < solutions.size(); ++i) {
                if (solutions.get(i).front == -1) continue;
                for (int j = 0; j < solutions.size(); ++j) {
                    if (!solutions.get(j).dominates.contains(i)) {
                        if (solutions.get(i).dominates(solutions.get(j))) {
                            solutions.get(i).addDominated(j);
                            ++solutions.get(j).dominatedBy;
                        }
                    }
                }
            }

            int front = 0;
            //Rasporedivanje u fronte
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
                    --solutions.get(i).dominatedBy;
                }
            }
            //Postavljanje fronti
            List<MOOPSolution>[] fronts = new List[front];
            for(int i = 0; i < front; ++i){
                fronts[i] = new ArrayList<>();
            }

            for(MOOPSolution s :solutions){
                s.fitness = 1./(s.front + 1);
                fronts[s.front].add(s);
            }

            //Dodavanje prvih n fronta
            List<MOOPSolution> chosenOne = null;
            solutions.clear();
            for(List<MOOPSolution> lms : fronts){
                if(solutions.size() + lms.size() > solutionSize){
                    chosenOne = lms;
                    break;
                }
                solutions.addAll(lms);
            }
            //Taman su stali
            if(chosenOne != null){
                //RacunanjeFitnessa
                for(MOOPSolution s : chosenOne){
                    s.fitness = 0;
                }

                for(int i = 0; i < function.getNumberOfObjectives(); ++i){
                    int pos = i;
                    chosenOne.sort(Comparator.comparingDouble(c -> c.values[pos]));

                    final double bigNumber = 100000.;

                    chosenOne.get(0).fitness += bigNumber;
                    chosenOne.get(chosenOne.size() - 1).fitness += bigNumber;

                    double minmax = chosenOne.get(chosenOne.size() - 1).values[i] - chosenOne.get(0).values[i];

                    for(int j = 1; j < chosenOne.size() - 1; ++j){
                        chosenOne.get(j).fitness += (chosenOne.get(j + 1).values[i] - chosenOne.get(j - 1).values[i]) / minmax;
                    }
                }

                chosenOne.sort(Comparator.comparingDouble(c->c.fitness));

                for(int i = 0, k = solutionSize - solutions.size(); i < k; ++i){
                    solutions.add(chosenOne.get(chosenOne.size() - 1 - i));
                }
            }

            //Print fronti
            if(iteration%10 == 0){
                System.out.print(iteration + ". ");
                for(int i = 0; i < front; ++i){
                    System.out.print((i + 1) + "{" + fronts[i].size() + "} ");
                }
                System.out.println();
            }

            if(iteration == maxIter - 1) break;
            MOOPSolution[] parents = new MOOPSolution[2];
            for(int i = 0; i < solutionSize; ++i){
                selection.select(solutions, parents);
                solutions.add(mutate.mutate(crossover.crossover(parents[0], parents[1])));
            }
            mutate.updateSigme();
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
