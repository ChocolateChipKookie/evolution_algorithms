package hr.fer.zemris.optjava.dz10;

import hr.fer.zemris.optjava.dz8.MultipleObjectiveSolution;

import java.util.*;

public class Tournament<T extends MultipleObjectiveSolution> {

    private Random rand = new Random();
    private int k;

    public Tournament(int k){
        this.k = k;
    }

    public void select(List<T> population, T[] parents){
        List<T> res = new ArrayList<>();

        for(int i = 0; i < k; ++i){
            T tmp;
            do {
                tmp = population.get(rand.nextInt(population.size()));
            } while (res.contains(tmp));
            res.add(tmp);
        }
        res.sort(Comparator.comparingDouble(t -> t.front));
        parents[0] = res.get(res.size() - 1);
        parents[1] = res.get(res.size() - 2);
    }
}
