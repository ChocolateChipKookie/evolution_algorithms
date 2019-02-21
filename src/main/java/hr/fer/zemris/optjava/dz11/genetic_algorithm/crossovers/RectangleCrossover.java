package hr.fer.zemris.optjava.dz11.genetic_algorithm.crossovers;

import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;

public interface RectangleCrossover {
    RectangleGenome crossover(RectangleGenome p1, RectangleGenome p2);
}
