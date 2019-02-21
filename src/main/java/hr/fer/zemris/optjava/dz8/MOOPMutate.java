package hr.fer.zemris.optjava.dz8;

import java.util.Random;

public class MOOPMutate {

    private Random rand;
    private double sigma;
    private double mutationFactor;
    private double update;
    private Boundaries[] bounds;

    public MOOPMutate(double sigma, double update, double mutationFactor , Boundaries[] bounds, Random rand){
        this.rand = rand;
        this.sigma = sigma;
        this.mutationFactor = mutationFactor;
        this.update = update;
        this.bounds = bounds;
    }

    public void updateSigme(){
        sigma*= update;
    }

    public MOOPSolution mutate(MOOPSolution child) {
        MOOPSolution res = child.newLikeThis();

        for(int i = 0; i<res.solution.length; ++i){
            if(rand.nextDouble() < mutationFactor){
                double tmp = rand.nextGaussian() * sigma;
                res.solution[i] += tmp;
                if(res.solution[i] > bounds[i].upper){
                    res.solution[i] =  bounds[i].upper - Math.abs(tmp);
                }else if(res.solution[i] < bounds[i].lower){
                    res.solution[i] =  bounds[i].lower + Math.abs(tmp);
                }
            }
        }
        return res;
    }
}
