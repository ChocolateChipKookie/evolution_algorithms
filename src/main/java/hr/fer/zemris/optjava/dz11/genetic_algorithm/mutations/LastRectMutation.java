package hr.fer.zemris.optjava.dz11.genetic_algorithm.mutations;

import hr.fer.zemris.optjava.dz11.gray_scale_image.Bounds;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class LastRectMutation implements RectangleMutation {

    private final double alpha;
    private final double mutationChance;
    private final double newGeneChance;
    private int aw;
    private int ah;
    private int ac;
    private Bounds bounds;

    public LastRectMutation(double alpha, double mutationChance, double newGeneChance, Bounds bounds){
        this.alpha = alpha;
        this.mutationChance = mutationChance;
        this.newGeneChance = newGeneChance;
        this.bounds = bounds;

        aw = (int)(bounds.width*alpha);
        ah = (int)(bounds.height*alpha);
        ac = (int)(255*alpha);
    }

    @Override
    public RectangleGenome mutate(RectangleGenome parent) {
        RectangleGenome res = parent.clone();
        int[] genome = res.getGenome();
        IRNG rand = RNG.getRNG();

        int i = res.currentRectangles - 1;

        if(rand.nextDouble() < newGeneChance){
            genome[5*i] = rand.nextInt(0, bounds.width);
            genome[5*i + 1] = rand.nextInt(0, bounds.height);
            genome[5*i + 2] = rand.nextInt(0, bounds.width - genome[5*i]);
            genome[5*i + 3] = rand.nextInt(0, bounds.height - genome[5*i + 1]);
            genome[5*i + 4] = rand.nextInt(0, 255);
        }
        else{
            if(rand.nextDouble() < mutationChance){
                int c0 = i * 5;
                genome[c0] = genome[c0] + rand.nextInt(-aw, aw);
                if(genome[c0] >= bounds.width) genome[c0] = bounds.width - 1;
                if(genome[c0] < 0) genome[c0] = 0;
            }
            if(rand.nextDouble() < mutationChance){
                int c0 = i * 5;
                int c2 = i * 5 + 2;
                genome[c2] = genome[c2] + rand.nextInt(-aw, aw);
                if(genome[c2] + genome[c0] >= bounds.width) genome[c2] = bounds.width - 1 - genome[c0];
                if(genome[c2] < 0) genome[c2] = 0;
            }
            if(rand.nextDouble() < mutationChance){
                int c1 = i * 5 + 1;
                genome[c1] = genome[c1] + rand.nextInt(-ah, ah);
                if(genome[c1] >= bounds.height) genome[c1] = bounds.height - 1;
                if(genome[c1] < 0) genome[c1] = 0;
            }
            if(rand.nextDouble() < mutationChance){
                int c1 = i * 5 + 1;
                int c3 = i * 5 + 3;
                genome[c3] = genome[c3] + rand.nextInt(-ah, ah);
                if(genome[c3] + genome[c1] >= bounds.height) genome[c3] = bounds.height - 1 - genome[c1];
                if(genome[c3] < 0) genome[c3] = 0;
            }
            if(rand.nextDouble() < mutationChance){
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
