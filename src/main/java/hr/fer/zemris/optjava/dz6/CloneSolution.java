package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.solutions.SingleObjectiveSolution;

import java.util.Random;

public class CloneSolution extends SingleObjectiveSolution {

    public double[] weights;
    private int topCounter = 0;

    public CloneSolution(int weights){
        this.weights = new double[weights];
    }

    public CloneSolution(int weights, double min, double max, Random rand){
        this.weights = new double[weights];
        for(int i = 0; i < weights; ++i){
            this.weights[i] = rand.nextDouble()*(max - min) + min;
        }
    }

    public void increaseCounter(){
        ++topCounter;
    }

    public int getTopCounter(){
        return topCounter;
    }

    public CloneSolution clone(){
        CloneSolution solution = new CloneSolution(0);
        solution.weights = this.weights.clone();
        solution.value = this.value;
        return solution;
    }
}
