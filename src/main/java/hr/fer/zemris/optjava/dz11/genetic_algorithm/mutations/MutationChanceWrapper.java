package hr.fer.zemris.optjava.dz11.genetic_algorithm.mutations;

public class MutationChanceWrapper {

    public final RectangleMutation mutation;
    public double chance;

    public MutationChanceWrapper(RectangleMutation mutation, double chance){
        this.mutation = mutation;
        this.chance = chance;
    }

}
