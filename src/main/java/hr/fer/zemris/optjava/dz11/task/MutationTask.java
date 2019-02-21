package hr.fer.zemris.optjava.dz11.task;

import hr.fer.zemris.optjava.dz11.genetic_algorithm.mutations.RectangleMutation;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;

import java.util.ArrayList;
import java.util.List;

public class MutationTask extends Task{
    private List<RectangleGenome> children;
    private RectangleMutation mutation;

    public MutationTask(List<RectangleGenome> children, RectangleMutation mutation){
        this.children = children;
        this.mutation = mutation;
    }

    @Override
    public Result work() {
        List<RectangleGenome> genomes = new ArrayList<>();

        for(RectangleGenome rg : children){
            genomes.add(mutation.mutate(rg));
        }

        return new Result(genomes);
    }
}
