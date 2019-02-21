package hr.fer.zemris.optjava.dz11.genetic_algorithm.mutations;

import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class RectangleSwitchMutation implements RectangleMutation {

    private int maxSwitches;

    public RectangleSwitchMutation(int maxSwitches){
        this.maxSwitches = maxSwitches;
    }

    @Override
    public RectangleGenome mutate(RectangleGenome parent) {
        RectangleGenome res = parent.clone();
        int[] genome = res.getGenome();
        IRNG rand = RNG.getRNG();

        int switches = rand.nextInt(0, maxSwitches);

        for(int i = 0; i < switches; ++i){
            int p1 = 5 * rand.nextInt(0, parent.currentRectangles);
            int p2 = 5 * rand.nextInt(0, parent.currentRectangles);
            for(int j = 0; j < 5; ++j){
                int tmp = genome[p1 + j];
                genome[p1 + j] = genome[p2 + j];
                genome[p2 + j] = tmp;
            }
        }

        return res;
    }

}
