package hr.fer.zemris.optjava.dz11.genetic_algorithm.mutations;

import hr.fer.zemris.optjava.dz11.gray_scale_image.Bounds;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class NewRectMutation implements RectangleMutation {

    private int maxNew;
    private Bounds bounds;

    public NewRectMutation(int maxNew, Bounds bounds){
        this.maxNew = maxNew;
        this.bounds = bounds;
    }


    @Override
    public RectangleGenome mutate(RectangleGenome parent) {
        RectangleGenome res = parent.clone();
        int[] genome = res.getGenome();
        IRNG rand = RNG.getRNG();

        int switches = rand.nextInt(0, maxNew);

        for(int i = 0; i < switches; ++i){
            int p = 5 * rand.nextInt(0, parent.currentRectangles);

            if (genome.length - 6 - p >= 0) System.arraycopy(genome, p + 5, genome, p, genome.length - 6 - p);

            genome[res.currentRectangles * 5 - 5] = rand.nextInt(0, bounds.width);
            genome[res.currentRectangles * 5 - 4] = rand.nextInt(0, bounds.height);
            genome[res.currentRectangles * 5 - 3] = rand.nextInt(0, bounds.width - genome[5*i + 1]);
            genome[res.currentRectangles * 5 - 2] = rand.nextInt(0, bounds.height - genome[5*i + 1]);
            genome[res.currentRectangles * 5 - 1] = rand.nextInt(0, 255);
        }

        return res;
    }
}
