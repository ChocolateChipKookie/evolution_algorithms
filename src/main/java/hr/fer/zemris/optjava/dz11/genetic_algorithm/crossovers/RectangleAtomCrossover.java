package hr.fer.zemris.optjava.dz11.genetic_algorithm.crossovers;

import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class RectangleAtomCrossover implements RectangleCrossover {

    private boolean elitistic;
    private double crossoverChance;

    public RectangleAtomCrossover(boolean elitistic, double crossoverChance){
        this.elitistic = elitistic;
        this.crossoverChance = crossoverChance;
    }

    public RectangleAtomCrossover(boolean elitistic){
        this(elitistic, 0.5);
    }

    @Override
    public RectangleGenome crossover(RectangleGenome p1, RectangleGenome p2) {
        RectangleGenome res;

        IRNG rand = RNG.getRNG();
        if(elitistic){
            if(p1.fitness < p2.fitness){
                RectangleGenome tmp = p1;
                p1 = p2;
                p2 = p1;
            }
        }
        else{
            if(rand.nextBoolean()){
                RectangleGenome tmp = p1;
                p1 = p2;
                p2 = p1;
            }
        }

        res = p1.clone();
        int[] genome1 = res.getGenome();
        int[] genome2 = p2.getGenome();

        for(int i = 0; i < res.currentRectangles; ++i){
            if(rand.nextDouble() < crossoverChance){
                int pos = i * 5;
                System.arraycopy(genome2, pos, genome1, pos, 5);
            }
        }

        return res;
    }
}
