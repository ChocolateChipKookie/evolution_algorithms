package hr.fer.zemris.optjava.dz8;

import java.util.*;

public class RouletteWheelSelection<T extends MultipleObjectiveSolution> {

    private Random rand;

    public RouletteWheelSelection(Random rand){
        this.rand = rand;
    }

    public void select(T[] generation, T[] parents){
        if(parents.length != 2){
            throw new RuntimeException("Not valid number of parents!");
        }

        ArrayList<T> solutions = new ArrayList<>();
        solutions.addAll(Arrays.asList(generation));

        double min = Collections.min(solutions, Comparator.comparingDouble(o -> o.fitness)).fitness * 0;

        double total = solutions.stream().mapToDouble(o -> o.fitness).sum();
        total -= min*solutions.size();

        for(int i = 0; i < parents.length; ++i){

            double res = rand.nextDouble()*total;
            T solution = null;
            //while(solution == null){
                for (T solution1 : solutions) {
                    res -= solution1.fitness - min;
                    if (res < 0) {
                        solution = solution1;
                        break;
                    }
                }
            //}
            try{
                total -= solution.fitness - min;
            }
            catch (Exception e){
                e.printStackTrace();
            }

            parents[i] = solution;
        }
    }

    public void select(List<T> generation, T[] parents){
        if(parents.length != 2){
            throw new RuntimeException("Not valid number of parents!");
        }

        ArrayList<T> solutions = new ArrayList<>(generation);

        double min = Collections.min(solutions, Comparator.comparingDouble(o -> o.fitness)).fitness * 0;

        double total = solutions.stream().mapToDouble(o -> o.fitness).sum();
        total -= min*solutions.size();

        for(int i = 0; i < parents.length; ++i){

            double res = rand.nextDouble()*total;
            T solution = null;
            while(solution == null){
            for (T solution1 : solutions) {
                res -= solution1.fitness - min;
                if (res < 0) {
                    solution = solution1;
                    break;
                }
            }
            }
            try{
                total -= solution.fitness - min;
            }
            catch (Exception e){
                e.printStackTrace();
            }

            parents[i] = solution;
        }
    }

}
