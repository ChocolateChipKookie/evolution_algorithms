package hr.fer.zemris.optjava.dz11.genetic_algorithm.mutations;

import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.List;

public class TotalMutation implements RectangleMutation {
    List<MutationChanceWrapper> mutations;

    public TotalMutation(List<MutationChanceWrapper> mutations){
        this.mutations = mutations;

        double sum = 0;
        for(MutationChanceWrapper mcw : mutations){
            sum += mcw.chance;
        }
        for(MutationChanceWrapper mcw : mutations){
            mcw.chance /= sum;
        }
    }

    @Override
    public RectangleGenome mutate(RectangleGenome genome) {
        double tmp = RNG.getRNG().nextDouble();

        for(MutationChanceWrapper mcw : mutations){
            tmp-=mcw.chance;
            if(tmp <= 0){
                return mcw.mutation.mutate(genome);
            }
        }

        return null;
    }
}
