package hr.fer.zemris.optjava.dz6;

import hr.fer.zemris.optjava.algorithms.neuralnetwork.IImuneMutate;

import java.util.Random;

public class CloneMutate implements IImuneMutate<CloneSolution> {

    private Random rand;
    private double mutationIntensity;

    public CloneMutate(Random rand, double mutationIntensity){
        this.rand = rand;
        this.mutationIntensity = mutationIntensity;
    }

    @Override
    public CloneSolution mutate(CloneSolution child, double mutationFactor) {
        for(int i = 0; i < child.weights.length; ++i){
            if(rand.nextDouble() < mutationFactor){
                child.weights[i] += (rand.nextDouble() * 2 - 1)*mutationIntensity;
            }
        }
        return child;
    }
}
