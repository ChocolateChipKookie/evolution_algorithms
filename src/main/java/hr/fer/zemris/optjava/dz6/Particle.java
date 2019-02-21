package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.WeightSolution;

import java.util.Arrays;
import java.util.Random;

public class Particle {
    public WeightSolution position;
    public double[] speed;
    public WeightSolution personalBest = null;

    public Particle(int solutionLen, SolutionBoundaries bounderies, Random rand){
        this.position = new WeightSolution(solutionLen);
        this.personalBest = new WeightSolution(0);
        this.personalBest.value = Double.MAX_VALUE;
        speed = new double[solutionLen];
        Arrays.setAll(position.weights, i -> bounderies.minPos + (bounderies.maxPos - bounderies.minPos) * rand.nextDouble());
        Arrays.setAll(speed, i -> bounderies.minSpeed + (bounderies.maxSpeed - bounderies.minSpeed) * rand.nextDouble());
    }

    public Particle(WeightSolution weights){
        this.position= weights.clone();
    }
}
