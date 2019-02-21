package hr.fer.zemris.optjava.dz11.genetic_algorithm;

import hr.fer.zemris.optjava.dz11.genetic_algorithm.RectangleGenome;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Tournament {

    private Random rand = new Random();
    private int k;

    public Tournament(int k){
        this.k = k;
    }

    public void select(RectangleGenome[] population, RectangleGenome[] parents){
        List<RectangleGenome> res = new ArrayList<>();

        for(int i = 0; i < k; ++i){
            RectangleGenome tmp;
            do {
                tmp = population[rand.nextInt(population.length)];
            } while (res.contains(tmp));
            res.add(tmp);
        }
        res.sort(Comparator.comparingDouble(t -> t.fitness));
        parents[0] = res.get(res.size() - 1);
        parents[1] = res.get(res.size() - 2);
    }

}
