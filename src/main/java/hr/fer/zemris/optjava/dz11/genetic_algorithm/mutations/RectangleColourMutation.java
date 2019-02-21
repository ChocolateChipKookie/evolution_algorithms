package hr.fer.zemris.optjava.dz11.genetic_algorithm.mutations;

import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class RectangleColourMutation implements RectangleMutation {

    private final double alpha;
    private final double mutationChance;
    private final double newGeneChance;
    private int ac;

    public RectangleColourMutation(double alpha, double mutationChance, double newGeneChance){
        this.alpha = alpha;
        this.mutationChance = mutationChance;
        this.newGeneChance = newGeneChance;
        ac = (int)(255*alpha);
    }

    @Override
    public RectangleGenome mutate(RectangleGenome parent) {
        RectangleGenome res = parent.clone();
        int[] genome = res.getGenome();
        IRNG rand = RNG.getRNG();

        for(int i = 0; i < res.currentRectangles; ++i){
            if(rand.nextDouble() < newGeneChance){
                genome[5*i + 4] = rand.nextInt(0, 255);
            }
            else if(rand.nextDouble() < mutationChance){
                int c4 = i * 5 + 4;
                genome[c4] = genome[c4] + rand.nextInt(-ac, ac);
                if(genome[c4] >= 256) genome[c4] = 255;
                if(genome[c4] < 0) genome[c4] = 0;
            }
        }

        if(rand.nextDouble() < newGeneChance){
            genome[genome.length - 1] = rand.nextInt(0, 255);
        }
        else if(rand.nextDouble() < mutationChance){
            genome[genome.length - 1] = genome[genome.length - 1] + rand.nextInt(-ac, ac);
            if(genome[genome.length - 1] >= 256) genome[genome.length - 1] = 255;
            if(genome[genome.length - 1] < 0) genome[genome.length - 1] = 0;
        }

        return res;
    }
}
