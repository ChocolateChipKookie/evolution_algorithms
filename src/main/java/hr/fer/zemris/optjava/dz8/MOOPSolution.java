package hr.fer.zemris.optjava.dz8;

import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MOOPSolution extends MultipleObjectiveSolution {

    public Set<Integer> dominates;
    public double[] solution;
    public int dominatedBy;
    public int neighbours;


    public MOOPSolution(int solutionSize, int resultsSize) {
        super(resultsSize);
        dominates = new HashSet<>();
        this.solution = new double[solutionSize];
    }

    public void addDominated(int dominated){
        dominates.add(dominated);
    }

    public static MOOPSolution getRandomSolution(int solutionSize, int resultsSize, Boundaries[] boundaries, Random rand){

        if(boundaries.length != solutionSize){
            throw new RuntimeException("Solution size and boundaries length don't match!");
        }
        MOOPSolution solution = new MOOPSolution(solutionSize, resultsSize);

        for(int i = 0; i < solutionSize; ++i){
            solution.solution[i] = rand.nextDouble()*(boundaries[i].upper - boundaries[i].lower) + boundaries[i].lower;
        }

        return solution;
    }

    public boolean dominates(MOOPSolution other){
        boolean atLeastOneBetter = false;
        for(int i = 0; i < this.values.length; ++i){
            double abs1 = Math.abs(this.values[i]);
            double abs2 = Math.abs(other.values[i]);
            if(abs1<abs2){
                atLeastOneBetter = true;
            }
            else if(abs1>abs2)return false;
        }
        return atLeastOneBetter;
    }

    public double valuesDistance(MOOPSolution other, Boundaries[] minmax){
        double result = 0;

        for(int i = 0; i < values.length; ++i){
            double tmp1 = values[i] - other.values[i];
            double tmp2 = (minmax[i].upper - minmax[i].lower);
            result += (tmp1 *tmp1) / (tmp2*tmp2);
        }

        return Math.sqrt(result);
    }

    public double solutionDistance(MOOPSolution other, Boundaries[] minmax){
        double result = 0;

        for(int i = 0; i < solution.length; ++i){
            double tmp1 = solution[i] - other.solution[i];
            double tmp2 = (minmax[i].upper - minmax[i].lower);
            result += (tmp1 *tmp1) / (tmp2*tmp2);
        }

        return Math.sqrt(result);
    }

    public MOOPSolution newLikeThis(){
        MOOPSolution tmp = new MOOPSolution(solution.length, values.length);
        tmp.solution = this.solution.clone();
        return tmp;
    }

}
