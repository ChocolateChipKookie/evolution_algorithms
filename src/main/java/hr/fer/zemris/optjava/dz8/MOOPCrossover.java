package hr.fer.zemris.optjava.dz8;

import java.util.Random;

public class MOOPCrossover {

    private Random rand;

    public MOOPCrossover(Random rand){
        this.rand = rand;
    }

    public MOOPSolution crossover(MOOPSolution s1, MOOPSolution s2){
        MOOPSolution res = s1.newLikeThis();

        for(int i = 0; i < res.solution.length; ++i){
            if(rand.nextDouble()<0.5){
                res.solution[i] = s2.solution[i];
            }
        }
        return res;
    }
}
