package hr.fer.zemris.optjava.dz11.task;

import hr.fer.zemris.optjava.dz11.genetic_algorithm.crossovers.RectangleCrossover;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;
import hr.fer.zemris.optjava.dz11.genetic_algorithm.Tournament;

import java.util.ArrayList;
import java.util.List;

public class CrossoverTask extends Task{
    private RectangleGenome[] population;
    private RectangleCrossover crossover;
    private Tournament tournament;
    private int children;

    public CrossoverTask(RectangleGenome[] population, RectangleCrossover crossover, Tournament tournament, int children){
        this.population = population;
        this.crossover = crossover;
        this.tournament = tournament;
        this.children = children;
    }

    @Override
    public Result work() {
        List<RectangleGenome> genomes = new ArrayList<>();

        RectangleGenome[] parents = new RectangleGenome[2];
        for(int i = 0; i < children; ++i){
            tournament.select(population, parents);
            genomes.add(crossover.crossover(parents[0], parents[1]));
        }

        return new Result(genomes);
    }
}
