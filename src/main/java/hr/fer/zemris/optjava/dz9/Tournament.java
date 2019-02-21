package hr.fer.zemris.optjava.dz9;

import java.util.*;

public class Tournament<T extends GPTree> {

    private int k;
    private Random rand;

    public Tournament(int k, Random rand){
        this.k = k;
        this.rand = rand;
    }

    public T select(T[] population){

        List<T> res = new ArrayList<>();

        for(int i = 0; i < k; ++i){
            T tmp;
            do {
                tmp = population[rand.nextInt(population.length)];
            } while (res.contains(tmp));
            res.add(tmp);
        }
        Optional<T> t = res.stream().max(Comparator.comparingDouble(k -> k.fitness));
        if(t.isPresent())return t.get();
        throw new RuntimeException("Tournament got a problem!");
    }

}
